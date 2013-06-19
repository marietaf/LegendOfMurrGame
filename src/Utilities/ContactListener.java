/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

/**
 *
 * @author Marieta
 */
public class ContactListener implements org.jbox2d.callbacks.ContactListener {

    boolean changeLevel = false;

    public void beginContact(Contact contact) {
        if (((contact.getFixtureA().getBody().getUserData() == "player") && (contact.getFixtureB().getBody().getUserData() == "transition"))
                || ((contact.getFixtureA().getBody().getUserData() == "transition") && (contact.getFixtureB().getBody().getUserData() == "player"))) {
            System.out.println("CONTACT BEGIN");
            changeLevel = true;
        }

    }

    public void endContact(Contact contact) {
        if (((contact.getFixtureA().getBody().getUserData() == "player") && (contact.getFixtureB().getBody().getUserData() == "transition"))
                || ((contact.getFixtureA().getBody().getUserData() == "transition") && (contact.getFixtureB().getBody().getUserData() == "player"))) {
            System.out.println("CONTACT END");
            changeLevel = false;
        }
    }

    public void preSolve(Contact contact, Manifold manifold) {
        //Get fixture from contact
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        //Find which fixture corresponds to which body + find y values
        float playerPositionY, platformPositionY;
        if ((fixtureA.getBody().getUserData() == "player" && fixtureB.getBody().getUserData() == "platform")
                || (fixtureA.getBody().getUserData() == "platform" && fixtureB.getBody().getUserData() == "player")) {
            if (fixtureA.getBody().getUserData() == "player") {
                playerPositionY = fixtureA.getBody().getPosition().y;
                platformPositionY = fixtureB.getBody().getPosition().y;
            }
            else{
                platformPositionY = fixtureA.getBody().getPosition().y;
                playerPositionY = fixtureB.getBody().getPosition().y;
            }
            if( playerPositionY <= (platformPositionY+10) ){
                contact.setEnabled(false);
            }
        }

    }

    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
    }

    public boolean GetLevelChange() {
        return changeLevel;
    }
}
