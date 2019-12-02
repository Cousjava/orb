/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.corba.ee.impl.presentation.rmi ;

/**
 * Holds information about the OMG IDL mapping of a Java type.
 */
public class IDLType {

    private Class cl_;

    // terminology for OMG IDL type package name
    private String[] modules_;

    // name of element within module
    private String memberName_;


    public IDLType(Class cl, String[] modules, String memberName) {
        cl_ = cl;
        modules_ = modules;
        memberName_ = memberName;
    }

    public IDLType(Class cl, String memberName) {
        this( cl, new String[0], memberName ) ;
    }

    public Class getJavaClass() {
        return cl_;
    }

    public String[] getModules()
    {
        return modules_ ;
    }
    
    public String makeConcatenatedName( char separator, boolean fixIDLKeywords ) {
        StringBuffer sbuff = new StringBuffer() ;
        for (int ctr=0; ctr<modules_.length; ctr++) {
            String mod = modules_[ctr] ;
            if (ctr>0)
                sbuff.append( separator ) ;
            
            if (fixIDLKeywords && IDLNameTranslatorImpl.isIDLKeyword(mod))
                mod = IDLNameTranslatorImpl.mangleIDLKeywordClash( mod ) ;

            sbuff.append( mod ) ;
        }

        return sbuff.toString() ;
    }
   
    public String getModuleName() {
        // Note that this should probably be makeConcatenatedName( '/', true )
        // for spec compliance,
        // but rmic does it this way, so we'll leave this.
        // The effect is that an overloaded method like
        // void foo( bar.typedef.Baz ) 
        // will get an IDL name of foo__bar_typedef_Baz instead of
        // foo__bar__typedef_Baz (note the extra _ before typedef).
        return makeConcatenatedName( '_', false ) ;
    }

    public String getExceptionName() {
        // Here we will check for IDL keyword collisions (see bug 5010332).
        // This means that the repository ID for 
        // foo.exception.SomeException is
        // "IDL:foo/_exception/SomeEx:1.0" (note the underscore in front
        // of the exception module name).
        String modName = makeConcatenatedName( '/', true ) ;

        String suffix = "Exception" ;
        String excName = memberName_ ;
        if (excName.endsWith( suffix )) {
            int last = excName.length() - suffix.length() ;
            excName = excName.substring( 0, last ) ;
        }
   
        // See bug 4989312: we must always add the Ex.
        excName += "Ex" ;

        if (modName.length() == 0)
            return "IDL:" + excName + ":1.0" ; 
        else
            return "IDL:" + modName + '/' + excName + ":1.0" ; 
    }

    public String getMemberName() {
        return memberName_;
    }
    
    /**
     * True if this type doesn't have a containing module.  This
     * would be true of a java type defined in the default package
     * or a primitive.
     * @return if there is a contained module.
     */
    public boolean hasModule() {
        return (modules_.length > 0) ;
    }
}
