import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Arrays;

public class BaseballElimination {

    private final int nTeams; // number of teams
    private ArrayList<String> teams;
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;


    public BaseballElimination(String filename) {
        In in = new In(filename);
        nTeams = in.readInt();
        teams = new ArrayList<>();
        w = new int[nTeams];
        l = new int[nTeams];
        r = new int[nTeams];
        g = new int[nTeams][nTeams];
        for (int i = 0; i < nTeams; i++) {
            teams.add(in.readString());
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            for (int j = 0; j < nTeams; j++) {
                g[i][j] = in.readInt();
            }
        }
        System.out.println(teams);
        System.out.println(Arrays.toString(w));
        System.out.println(Arrays.toString(l));
        System.out.println(Arrays.toString(r));
        System.out.println(Arrays.deepToString(g));

    }


    // number of teams
    public int numberOfTeams() {
        return nTeams;
    }

    // all teams
    public Iterable<String> teams() {
        return teams;
    }

    // number of wins for the given team
    public int wins(String teams) {
        int i = this.teams.indexOf(teams);
        return w[i];
    }

    // number of losses for the given team
    public int losses(String teams) {
        int i = this.teams.indexOf(teams);
        return  l[i];
    }

    // number of remaining games for the given team
    public int remaining(String teams) {
        int i = this.teams.indexOf(teams);
        return r[i];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        int i1 = this.teams.indexOf(team1);
        int i2 = this.teams.indexOf(team2);
        return g[i1][i2];
    }

//    // is the given team eliminated?
//    public boolean isEliminated(String team) {
//
//    }
//
//    // subset R of teams that eliminates given team; null if not eliminated
//    public Iterable<String> certificateOfElimination(String team) {
//
//    }

    public static void main(String[] args) {
        BaseballElimination baseballElimination = new BaseballElimination("//home/yiclzh/Downloads/baseball/teams4.txt");
        System.out.println(baseballElimination.numberOfTeams());
        System.out.println(baseballElimination.teams());
        System.out.println(baseballElimination.against("Atlanta", "New_York"));
        System.out.println(baseballElimination.losses("Montreal"));
        System.out.println(baseballElimination.wins("Philadelphia"));
        System.out.println(baseballElimination.remaining("New_York"));
    }
}
