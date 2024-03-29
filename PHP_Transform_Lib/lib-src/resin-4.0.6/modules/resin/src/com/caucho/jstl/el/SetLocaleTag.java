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
 *   Free SoftwareFoundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Scott Ferguson
 */

package com.caucho.jstl.el;

import com.caucho.el.Expr;
import com.caucho.jsp.PageContextImpl;
import com.caucho.util.L10N;

import javax.el.ELContext;
import javax.el.ELException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Locale;

/**
 * Sets the i18n locale bundle for the current page.
 */
public class SetLocaleTag extends TagSupport {
  private static L10N L = new L10N(SetBundleTag.class);
  
  private Expr _valueExpr;
  private Expr _variantExpr;
  private String _scope;

  /**
   * Sets the JSP-EL expression for the locale value.
   */
  public void setValue(Expr value)
  {
    _valueExpr = value;
  }

  /**
   * Sets the JSP-EL expression for the variant value.
   */
  public void setVariant(Expr variant)
  {
    _variantExpr = variant;
  }

  /**
   * Sets the scope to store the bundle.
   */
  public void setScope(String scope)
  {
    _scope = scope;
  }

  /**
   * Process the tag.
   */
  public int doStartTag()
    throws JspException
  {
    try {
      PageContextImpl pageContext = (PageContextImpl) this.pageContext;
      ELContext env = pageContext.getELContext();

      Object valueObj = _valueExpr.evalObject(env);
      Locale locale = null;

      if (valueObj instanceof Locale) {
	locale = (Locale) valueObj;
      }
      else if (valueObj instanceof String) {
	String variant = null;

	if (_variantExpr != null)
	  variant = _variantExpr.evalString(env);

	locale = pageContext.getLocale((String) valueObj, variant);
      }

      CoreSetTag.setValue(pageContext, Config.FMT_LOCALE, _scope, locale);

      return SKIP_BODY;
    } catch (ELException e) {
      throw new JspException(e);
    }
  }
}
