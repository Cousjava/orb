/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 1998-1999 IBM Corp. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

// This class puts turn taking into a thread so that the UI remains
// responsive.  You take a turn by pressing the "Take Turn" button.
// The button then becomes "Waiting for other players" and may stay
// that way for a while, until all other players have pushed their
// buttons.  During this time, your turn call is blocked in the server.
// But because I didn't like being stuck during this time, I made it
// possible to do anything but take another turn while waiting for
// other players.  So you can start working on your other turn while
// waiting for other players to finish this turn.  In reality, though,
// some of the things you do will affect the current turn that is blocked
// and waiting for other players.  This obviously needs more work, but
// it's a start!

package ibmspace.client;

public class TurnTaker extends Thread
{
    private GameUI       fUI;

    public TurnTaker (GameUI ui)
    {
        fUI = ui;
    }

    public void run ()
    {
        fUI.beginTurn ();
        fUI.endTurn ();
    }

}
