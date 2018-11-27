package game.model.movables;

public interface Visitable {
    void acceptProbe(StoneMiner.StoneProbe probe);

    void acceptProbe(Lumberman.LumberProbe probe);

    void acceptProbe(Warrior.AttackProbe probe);

    void acceptProbe(SimpleProbe probe);

    void acceptProbe(Transporter.RoadProbe probe);

    void acceptProbe(Ork.OrkProbe probe);
}
