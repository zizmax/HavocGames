package com.zizmax.HavocGames;


public class Team {
	String name;
	int team;
	int score;
	String title;
	boolean playing;
	
	void setTeam(int newvalue){
        team = newvalue;
    }

    int getTeam(){
        return team;
	}

	void setName(String newname){
		name = newname;
	}

	String getName(){
	    return name;
	}


	void setScore(int newscore){
        score = newscore;
    }

	int getScore(){
		return score;
	}


    void setTitle(String newtitle){
        title = newtitle;
    }

	String getTitle(){
	    return title;
    }


	void setPlaying(boolean newplaying){
	    playing = newplaying;
    }

	boolean getPlaying(){
	    return playing;
	}

}
