package game.model.objects.buildings;

import game.model.movables.CarriableResource;
import game.model.movables.ResourceCollector;
import game.model.world.Field;

import java.io.Serializable;

public class CollectorHouse<T extends ResourceCollector> extends MedBuilding {
        private int workersInside;

    protected void setCreator(Creator creator) {
        this.creator = creator;
    }

    private Creator creator;
        public CollectorHouse(Field p, int defaultWorkersNumber) {
            super(p);
            workersInside = defaultWorkersNumber;
            priceToEnable(3, 1);
        }

        protected abstract class Creator implements Serializable {
            protected abstract void create();
        }

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

