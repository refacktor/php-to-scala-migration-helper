/*
 * Copyright (c) 1998-2010 Caucho Technology -- all rights reserved
 *
 * This file is part of Resin(R) Open Source
 *
 * Each copy or derived work must preserve the copyright notice and this
 * notice unmodified.
 *
 * Resin Open Source is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Resin Open Source is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, or any warranty
 * of NON-INFRINGEMENT.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Resin Open Source; if not, write to the
 *
 *   Free Software Foundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Scott Ferguson
 */

package com.caucho.quercus.marshal;

import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.JavaValue;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.expr.Expr;
import com.caucho.quercus.lib.file.BinaryInput;
import com.caucho.quercus.lib.file.BinaryOutput;
import com.caucho.quercus.lib.file.ReadStreamInput;
import com.caucho.quercus.lib.file.WriteStreamOutput;

import java.io.InputStream;
import java.io.OutputStream;

public class BinaryOutputMarshal extends Marshal
{
  public static final Marshal MARSHAL = new BinaryOutputMarshal();
  
  public boolean isReadOnly()
  {
    return true;
  }

  public Object marshal(Env env, Expr expr, Class expectedClass)
  {
    return marshal(env, expr.eval(env), expectedClass);
  }

  public Object marshal(Env env, Value value, Class expectedClass)
  {
    if (value == null)
      return null;
    else if (value instanceof BinaryOutput)
      return (BinaryOutput) value;

    Object javaObj = value.toJavaObject();

    if (javaObj instanceof BinaryOutput)
      return (BinaryOutput) javaObj;
    else if (javaObj instanceof OutputStream)
      return new WriteStreamOutput((OutputStream) javaObj);
    else
      throw new IllegalStateException(L.l("Cannot marshal {0} to BinaryOutput",
                                          javaObj));
  }

  public static BinaryOutput marshal(Env env, Value value)
  {
    if (value == null)
      return null;
    else if (value instanceof BinaryOutput)
      return (BinaryOutput) value;

    Object javaObj = value.toJavaObject();

    if (javaObj instanceof BinaryOutput)
      return (BinaryOutput) javaObj;
    else if (javaObj instanceof OutputStream)
      return new WriteStreamOutput((OutputStream) javaObj);
    else
      throw new IllegalStateException(L.l("Cannot marshal {0} to BinaryOutput",
                                          javaObj));
  }

  public Value unmarshal(Env env, Object value)
  {
    return (Value) value;
  }
  
  @Override
  protected int getMarshalingCostImpl(Value argValue)
  {
    if (argValue instanceof JavaValue
	&& OutputStream.class.isAssignableFrom(argValue.toJavaObject().getClass()))
      return Marshal.ZERO;
    else
      return Marshal.FOUR;
  }
  
  @Override
  public Class getExpectedClass()
  {
    return BinaryOutput.class;
  }
}
