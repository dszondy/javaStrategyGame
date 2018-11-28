package game.model.movables;

/**
 * Interface for the probes that collect field info
 */
public interface Visitable {
    /**
     * @param probe the probe that visits it
     */
    void acceptProbe(StoneMiner.StoneProbe probe);
    /**
     * @param probe the probe that visits it
     */
    void acceptProbe(Lumberjack.LumberProbe probe);
    /**
     * @param probe the probe that visits it
     */
    void acceptProbe(Warrior.AttackProbe probe);
    /**
     * @param probe the probe that visits it
     */
    void acceptProbe(SimpleProbe probe);
    /**
     * @param probe the probe that visits it
     */
    void acceptProbe(Transporter.RoadProbe probe);
    /**
     * @param probe the probe that visits it
     */
    void acceptProbe(Ork.OrkProbe probe);
}
