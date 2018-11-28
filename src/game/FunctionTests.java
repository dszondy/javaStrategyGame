package game;

import game.model.Visible;
import game.model.movables.*;
import game.model.objects.Rock;
import game.model.objects.Sign;
import game.model.objects.Tree;
import game.model.objects.WorldObject;
import game.model.objects.buildings.*;
import game.model.resources.Resource;
import game.model.world.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FunctionTests {
    World world;
    @Before
    public void setUp() throws Exception {
        world = new World();
        WorldObject.setWorld(world);
    }

    @Test
    public void testMainBuildingBuilt() {
        MainBuilding m = new MainBuilding(world.getFieldAtLocation(new Point(10, 10)));
        assertTrue("The main building is not set successfully",world.getPlayersMainBuilding()==m);
        assertTrue("The world not contains the main building", world.GetVisibleList().contains(m));
    }

    @Test
    public void testTreeCut() {
        new MainBuilding(world.getFieldAtLocation(new Point(10, 10)));
        Tree tree = new Tree(world.getFieldAtLocation(new Point(30, 30)));
        assertTrue("The tree is not alive", tree.isAlive());
        assertTrue("Tree cant be cut", tree.cut());
        assertFalse("The tree is not dead after being cut", tree.isAlive());
        assertFalse("Dead trees shouldn't be cut anymore", tree.cut());
    }

    @Test
    public void testRockMine() {
        new MainBuilding(world.getFieldAtLocation(new Point(10, 10)));
        Rock rock = new Rock(world.getFieldAtLocation(new Point(30, 30)));
        for(int i=0; i<100;i++) {
            assertTrue("The Rock is not active", rock.isAlive());
            assertTrue("Tree cant be cut", rock.mineStone());
        }
        assertFalse("The Rock is not empty after being fully mined", rock.isAlive());
        assertFalse("Shouldn't mine more", rock.mineStone());
    }

    @Test
    public void WoodCutterFindTree() {
        new MainBuilding(world.getFieldAtLocation(new Point(10, 10)));
        Tree tree = new Tree(world.getFieldAtLocation(new Point(30, 30)));
        LumbererHouse house = new LumbererHouse(world.getFieldAtLocation(new Point(25, 25)));
        Lumberjack lumberjack = new Lumberjack(house);
        Path<Tree> p = lumberjack.FindTree(Integer.MAX_VALUE);
        assertTrue("the tree is not found",p.getObj() == tree);
    }


    @Test
    public void StoneMinerFindRock() {
        new MainBuilding(world.getFieldAtLocation(new Point(10, 10)));
        Rock rock = new Rock(world.getFieldAtLocation(new Point(30, 30)));
        StoneMine house = new StoneMine(world.getFieldAtLocation(new Point(25, 25)));
        StoneMiner stoneMiner = new StoneMiner(house);
        Path<Rock> p = stoneMiner.FindRock(Integer.MAX_VALUE);
        assertTrue("the Rock is not found",p.getObj() == rock);
    }
    @Test

    public void WarriorFindEnemy() {
        new MainBuilding(world.getFieldAtLocation(new Point(10, 10)));
        EnemyStronghold enemy = new EnemyStronghold(world.getFieldAtLocation(new Point(30, 30)));
        Stronghold house = new Stronghold(world.getFieldAtLocation(new Point(25, 25)));
        Warrior warrior = new Warrior(house);
        EnemyStronghold f = warrior.FindEnemy(Integer.MAX_VALUE);
        assertTrue("Enemy not found",f == enemy);
    }

    @Test
    public void EnemyFindBuilding() {
        MainBuilding m = new MainBuilding(world.getFieldAtLocation(new Point(10, 10)));
        EnemyStronghold enemy = new EnemyStronghold(world.getFieldAtLocation(new Point(17, 17)));
        Ork ork = new Ork(enemy);
        Path<Building> f = ork.FindAttackable(Integer.MAX_VALUE);
        assertTrue("Building not found",f.getObj() == m);
    }

    @Test
    public void RoadBuilding() {
        MainBuilding m = new MainBuilding(world.getFieldAtLocation(new Point(10, 10)));
        Sign s = new Sign(world.getFieldAtLocation(new Point(15, 15)));
        Sign s2 = new Sign(world.getFieldAtLocation(new Point(100, 100)));
        Transporter t1 = new Transporter(m.getEntry(), s);
        Transporter t2 = new Transporter(m.getEntry(), s2);
        t1.tick();
        assertTrue("Road not build",t1.isAlive() );
        t2.tick();
        assertFalse("Too long road",t2.isAlive() );


    }

    @Test
    public void MainbuildingTickPick() {
        MainBuilding m = new MainBuilding(world.getFieldAtLocation(new Point(10, 10)));
        assertTrue("Starting resource is invalid",m.getResources().get(Resource.STONE) == 100);

        m.getEntry().place(new CarriableResource(Resource.STONE, m.getEntry(), m));
        m.tick();
        assertTrue("Resource not added",m.getResources().get(Resource.STONE) == 101);
    }

    @Test
    public void PackagePlanRoad() {
        MainBuilding m = new MainBuilding(world.getFieldAtLocation(new Point(10, 10)));
        Sign s1= new Sign(world.getFieldAtLocation(new Point(15, 15)));
        Sign s2 = new Sign(world.getFieldAtLocation(new Point(20, 20)));
        Transporter t1 = new Transporter(m.getEntry(), s1);
        Transporter t2 = new Transporter(s1, s2);
        CarriableResource r = new CarriableResource(Resource.STONE, s2, m);
        s2.place(r);
        assertTrue("Not found road", r.getNext()==s1);
    }

    @Test
    public void FindObject() {
        MainBuilding m = new MainBuilding(world.getFieldAtLocation(new Point(10, 10)));
        new Sign(world.getFieldAtLocation(new Point(15, 15)));
        new Sign(world.getFieldAtLocation(new Point(20, 20)));
        new StoneMine(world.getFieldAtLocation(new Point(25, 25)));
        Path<WorldObject> p = Path.FindPath(world.getFieldAtLocation(new Point(28, 28)), m, Integer.MAX_VALUE);
        assertTrue("Not found the object", p.getObj()==m);
        assertTrue("The road is too short, invalid", p.getLength()>=25);
        assertTrue("The road is too long, not the short enough", p.getLength()<=30);

    }


    @After
    public void tearDown() throws Exception {
        WorldObject.setWorld(null);
    }
}
