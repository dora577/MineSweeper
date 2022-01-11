enum PositionState {
    Nothing,Mine,Explosion,AllExplode;

}

public class Position {
    private boolean flagged;
    private boolean hidden;
    private PositionState state;

    public boolean isHidden(){return hidden; }

    public boolean isFlagged(){return flagged;}

    public PositionState isState(){return state;}
    public void reveal() { hidden = false; }
    public void explode() { state = PositionState.Explosion; }
    public void explodeAll(){state = PositionState.AllExplode;}
    public void changeFlag(){flagged = !flagged;}



    public Position(boolean isMine){
        flagged = false;
        hidden = true;
        if(isMine == true)
            state = PositionState.Mine;
        else
            state = PositionState.Nothing;

    }


}
