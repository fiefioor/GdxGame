/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdx.game.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.gdx.game.sprites.InteractiveTileObject;

/**
 *
 * @author fiefioor
 */
public class WolrdContactListener implements ContactListener {

    @Override
    public void beginContact(Contact cntct) {
       Fixture fixA = cntct.getFixtureA();
       Fixture fixB = cntct.getFixtureB();
       
       if(fixA.getUserData() == "head" || fixB.getUserData() == "head"){
           Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
           Fixture object = head == fixA ? fixB : fixA;
           
           if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
               ((InteractiveTileObject) object.getUserData()).onHeadHit();
               
           }
       }
       
       
    }

    @Override
    public void endContact(Contact cntct) {
       
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {
       
    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {
      
    }
    
}
