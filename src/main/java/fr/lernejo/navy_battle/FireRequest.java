package fr.lernejo.navy_battle;

public class FireRequest {
    public final String consequence;
    public final boolean shipLeft;

    public FireRequest(String consequence, boolean shipLeft) {
        this.consequence = consequence;
        this.shipLeft = shipLeft;
    }
}
