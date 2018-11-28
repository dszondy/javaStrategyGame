package game;

import game.model.Visible;
import game.model.movables.Transporter;
import game.model.objects.Rock;
import game.model.objects.Tree;
import game.model.objects.WorldObject;
import game.model.objects.buildings.*;
import game.model.world.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestClass {
    World world;
    @Before
    public void setUp() throws Exception {
        world = new World();
        WorldObject.setWorld(world);
    }

    @Test
    public void testEnemyAttacks(){
        MainBuilding mainBuilding = new MainBuilding(world.getFieldAtLocation(new Point(5, 5)));
        Building building = new StoneMine(world.getFieldAtLocation(new Point(64, 64)));
        new EnemyStronghold(world.getFieldAtLocation(new Point(74, 64)));
        assertTrue("the building died befour stepping", building.isAlive());
        for(int i=0; i<10000 && mainBuilding.isAlive(); i++){
            try {
                world.tickAll();
            }catch (Exception e){};
        }
        assertFalse("the building has not been destroyed", building.isAlive());

    }

    @Test
    public void testWoodCutter(){
        MainBuilding mainBuilding = new MainBuilding(world.getFieldAtLocation(new Point(5, 5)));
        Building building = new LumbererHouse(world.getFieldAtLocation(new Point(64, 64)));
        building.enable();
        Tree t = new Tree(world.getFieldAtLocation(new Point(70, 64)));
        assertTrue("the tree died befour stepping", t.isAlive());
        for(int i=0; i<10000 && t.isAlive(); i++){
            try {
                world.tickAll();
            }catch (Exception e){};
        }
        assertFalse("the tree has not been cutted", t.isAlive());
    }

    @Test
    public void testStoneMine(){
        MainBuilding mainBuilding = new MainBuilding(world.getFieldAtLocation(new Point(5, 5)));
        Building building = new StoneMine(world.getFieldAtLocation(new Point(64, 64)));
        building.enable();
        Rock r = new Rock(world.getFieldAtLocation(new Point(70, 64)));
        assertTrue("the rock 'died' befour stepping", r.isAlive());
        for(int i=0; i<100000 && r.isAlive(); i++){
            try {
                world.tickAll();
            }catch (Exception e){};
        }
        assertFalse("the rock has not been mined fully", r.isAlive());
    }

    @Test
    public void testResourceTransportAndBuildUp(){
        MainBuilding mainBuilding = new MainBuilding(world.getFieldAtLocation(new Point(64, 64)));
        Building b1 = new StoneMine(world.getFieldAtLocation(new Point(74, 64)));
        Building b2 = new LumbererHouse(world.getFieldAtLocation(new Point(54, 64)));
        new Transporter(mainBuilding.getEntry(), b1.getEntry());
        new Transporter(mainBuilding.getEntry(), b2.getEntry());
        assertFalse("b1 Building is enabled before building", b1.isEnabled());
        assertFalse("b2 Building is enabled before building", b2.isEnabled());
        for(int i=0; i<10000; i++){
            try {
                world.tickAll();
            }catch (Exception e){};
        }
        assertTrue("b1 Building has not been build", b1.isEnabled());
        assertTrue("b1 Building has not been build\"", b2.isEnabled());
    }

    @Test
    public void TreeBehave(){
        MainBuilding mainBuilding = new MainBuilding(world.getFieldAtLocation(new Point(64, 64)));
        new Tree(world.getFieldAtLocation(new Point(74, 64)));
        for(int i=0; i<10000; i++){
            try {
                world.tickAll();
            }catch (Exception e){};
        }
        Integer treeCount = 0;
        for (Visible v :world.GetVisibleList()){
            if(v instanceof Tree){
                treeCount++;
            }
        }
        assertTrue("The tree's had not spread",treeCount>1);
    }




    @After
    public void tearDown() throws Exception {
        WorldObject.setWorld(null);
    }
}
