package game.model.objects.buildings;

import game.model.movables.CarriableResource;
import game.model.movables.ResourceCollector;
import game.model.world.Field;

import java.io.Serializable;

/**
 * Medium building for buildings with workers (licke stone miner or lumberjack)
 * @param <T> the worker type
 */
public class CollectorHouse<T extends ResourceCollector> extends MedBuilding {
        private int workersInside;

    /**
     * Sets a creator, so it can send workers with it
     * @param creator
     */
    protected void setCreator(Creator creator) {
        this.creator = creator;
    }

    /**
     * the creator it uses te create workers
     */
    private Creator creator;

    /**
     * Creates a house
     * @param p the entry's location
     * @param defaultWorkersNumber the count of workers
     */
        public CollectorHouse(Field p, int defaultWorkersNumber) {
            super(p);
            workersInside = defaultWorkersNumber;
            priceToEnable(5, 2);
        }

    /**
     * it uses this to create new workers
     */
    protected abstract class Creator implements Serializable {
            protected abstract void create();
        }

    /**
     * If it has workers it sends them to work(create)
     * @return
     */
        @Override
        public boolean tick() {
            if (isAlive()) {
                if(!isEnabled()){
                    tryGetResource();
                    return true;
                }
                if (workersInside > 0) {
                    creator.create();
                    workersInside--;
                }
                return true;
            }
            return false;
        }

    /**
     * Accepts the returning worker, and sends their resources to the main building
     * @param l
     */
    public void acceptReturning(ResourceCollector l) {
            workersInside++;
            if (l.hasResource()) {
                CarriableResource r = l.pickResource();
                CarriableResource pack = new CarriableResource(r.getR(), getEntry(), MainBuilding.getAddress());
                this.getEntry().place(pack);
                r.die();
            }
            l.die();
        }
    }

