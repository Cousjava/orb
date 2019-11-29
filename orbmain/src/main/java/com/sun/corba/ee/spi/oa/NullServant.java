/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.corba.ee.spi.oa ;

import org.omg.CORBA.SystemException ;

/** NullServant is used to represent a null servant returned 
 * OAInvocationInfo after a 
 * ObjectAdapter.getInvocationServant( OAInvocationInfo ) call.
 * If the getInvocationServant call could not locate a servant
 * for the ObjectId in the OAInvocationInfo, getServantContainer
 * will contain a NullServant.  Later stages of the request 
 * dispatch may choose either to throw the exception or perform
 * some other action in response to the NullServant result.
 */
public interface NullServant 
{
    /** Obtain the exception that is associated with this 
     * NullServant instance.
     * @return the associated exception
     */
    SystemException getException() ;
}
