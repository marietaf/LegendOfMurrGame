/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilities;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

/**
 *
 * @author Marieta
 */
public class ContactListener implements org.jbox2d.callbacks.ContactListener {

    boolean changeLevels;

    public void beginContact(Contact contact) {
        if( ((contact.getFixtureA().getUserData() == "player") && (contact.getFixtureB().getUserData() == "transition")) ||
            ((contact.getFixtureA().getUserData() == "transition") && (contact.getFixtureB().getUserData() == "player"))){
            changeLevels = true;
        }
    }

    public void endContact(Contact contact) {

    }

    public void preSolve(Contact contact, Manifold manifold) {

    }

    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    public boolean GetLevelChange(){
        return changeLevels;
    }

}
