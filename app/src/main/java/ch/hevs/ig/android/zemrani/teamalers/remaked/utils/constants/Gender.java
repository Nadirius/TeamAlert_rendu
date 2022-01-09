package ch.hevs.ig.android.zemrani.teamalers.remaked.utils.constants;

public enum Gender {

    MALE("male"),
    FEMALE("female"),
    NON_BINARY("non_binary");
    public final String label;
    
    Gender( String label ) {
        this.label = label;
    }
}
