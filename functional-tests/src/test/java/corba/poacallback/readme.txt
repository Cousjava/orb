#
# Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Distribution License v. 1.0, which is available at
# http://www.eclipse.org/org/documents/edl-v10.php.
#
# SPDX-License-Identifier: BSD-3-Clause
#

Author: Hemanth Puttaswamy

This test validates that we don't hold the poa mutex lock for the POA upcalls
like ServantLocator.preinvoke and postinvoke. This test contains 

- two IDL interfaces idlI1 with operations o1 and o2 and idliI2 with operation o 
- two ServantLocators idliI1ServantLocator and idliI2ServantLocator for 
  respective servants implementing the idl interfaces


The client invokes idliI1.o1, this would result in 
idlI1ServantLocator.preinvoke() being called on the Server side. The preinvoke 
method calls out to idliI2.o, if the operation name is 'o1'. The 
idliI2ServantLocator.preinvoke() will call back to 'idliI1.o2". If all these 
calls succeed without hanging then the test passed, if not the test will hang 
for a while and the framework will time out resulting in a test failure.

