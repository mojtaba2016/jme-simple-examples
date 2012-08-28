/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.editor;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.export.Savable;
import com.jme3.input.InputManager;
import com.jme3.math.*;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import com.jme3.system.AppSettings;


public class EditorCameraMoveControl extends AbstractControl implements Savable, Cloneable{

    
    private Node spatial;
    private InputManager inputMan;
    private Vector2f startPosMouse, endPosMouse, ceneterScr;
    private Vector3f  spatMove, camMoveX, camMoveY;
    private float mouseDist;
    private Camera camera;
    private AppSettings settings;
    
    public EditorCameraMoveControl (Node camHelper, InputManager input, Camera cam, AppSettings aps) {
        
        
        spatial = camHelper;
        inputMan = input;
        camera = cam;
        settings = aps;
        
        initControl();
        
        
    }
    
    
    private void initControl(){
        
        System.out.println("Hi Camera Move" + inputMan.getCursorPosition().toString());
       
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        // center of the screen
        float width = camera.getWidth() * 0.5f;
        float height = camera.getHeight() * 0.5f;
        ceneterScr = new Vector2f(width, height);   
        
        endPosMouse = inputMan.getCursorPosition();
        mouseDist = ceneterScr.distance(endPosMouse);

        
        
        camMoveX = camera.getLeft();
        if (endPosMouse.x > ceneterScr.x) camMoveX.negateLocal();
        camMoveX.normalizeLocal();
                
        camMoveY = camera.getUp();
        if (endPosMouse.y < ceneterScr.y) camMoveY.negateLocal();        
        camMoveY.normalizeLocal();
        
        
        float coefficient = (endPosMouse.y-ceneterScr.y) / (settings.getHeight()*0.5f);
        if (endPosMouse.y < ceneterScr.y) coefficient= -coefficient;
        
//        spatMove = camMoveX.add(camMoveY).normalizeLocal();
        spatMove = camMoveX.interpolate(camMoveY, coefficient);
        
        System.out.println(coefficient);
        
        spatial.move(spatMove.multLocal(tpf*(mouseDist*0.1f)));
        
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
