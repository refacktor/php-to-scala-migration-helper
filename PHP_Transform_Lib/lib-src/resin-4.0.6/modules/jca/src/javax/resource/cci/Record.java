/*
 * Copyright (c) 1998-2003 Caucho Technology -- all rights reserved
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
 *   Free SoftwareFoundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Scott Ferguson
 */

package javax.resource.cci;

import java.io.Serializable;

/**
 * Represents input or output to the execute methods.
 */
public interface Record extends Cloneable, Serializable {
  /**
   * Returns the name of the record.
   */
  public String getRecordName();

  /**
   * Sets the name of the record.
   */
  public void setRecordName(String name);

  /**
   * Sets a short description for the record.
   */
  public void setRecordShortDescription(String description);

  /**
   * Gets a short description for the record.
   */
  public String getRecordShortDescription();

  public Object clone() throws CloneNotSupportedException;

  public boolean equals(Object o);

  public int hashCode();
}
