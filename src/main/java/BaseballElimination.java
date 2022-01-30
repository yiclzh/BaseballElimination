import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BaseballElimination {

    private final int nTeams;
    private ArrayList<String> teams;
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;


    public BaseballElimination(String filename) {
        if (filename == null) { throw new IllegalArgumentException("File cannot be null."); }
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
        if (!this.teams.contains(teams) || teams == null) { throw new IllegalArgumentException("Illegal Argument"); }
        int i = this.teams.indexOf(teams);
        return w[i];
    }

    // number of losses for the given team
    public int losses(String teams) {
        if (!this.teams.contains(teams) || teams == null) { throw new IllegalArgumentException("Illegal Argument"); }
        int i = this.teams.indexOf(teams);
        return  l[i];
    }

    // number of remaining games for the given team
    public int remaining(String teams) {
        if (!this.teams.contains(teams) || teams == null) { throw new IllegalArgumentException("Illegal Argument"); }
        int i = this.teams.indexOf(teams);
        return r[i];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (!teams.contains(team1) || team1 == null) { throw new IllegalArgumentException("Illegal Argument"); }
        if (!teams.contains(team2) || team2 == null) { throw new IllegalArgumentException("Illegal Argument"); }
        int i1 = this.teams.indexOf(team1);
        int i2 = this.teams.indexOf(team2);
        return g[i1][i2];
    }

    // is the given team eliminated?
    public boolean isEliminated(String team) {
        if (!teams.contains(team) || team == null) { throw new IllegalArgumentException("Illegal Argument"); }
        if (certificateOfElimination(team) == null) {
            return false;
        } else {
            return true;
        }
    }

    private FlowNetwork constructFlowNetwork(int x) {
        int s = nTeams;
        int t = s + 1;
        int gameNode = nTeams + 2;
        Set<FlowEdge> edges = new HashSet<>();
        for (int i = 0; i < nTeams; i++) {
            if ( i != x && w[x] + r[x] >= w[i]) {
                for (int j = i+1; j < nTeams; j++) {
                    if (j != x) {
                        edges.add(new FlowEdge(s, gameNode, g[i][j]));
                        edges.add(new FlowEdge(gameNode, i, Double.POSITIVE_INFINITY));
                        edges.add(new FlowEdge(gameNode, j, Double.POSITIVE_INFINITY));
                        gameNode++;
                    }
                }
                edges.add(new FlowEdge(i, t, w[x] + r[x] - w[i]));
            }
        }

        FlowNetwork flowNetwork = new FlowNetwork(gameNode);
        for (FlowEdge edge : edges) {
            flowNetwork.addEdge(edge);
        }

        return flowNetwork;
    }

    private boolean isTriviallyElimination(int x) {
        for (String s : teams) {
            int i = teams.indexOf(s);
            if (w[x] + r[x] < w[i]) {
                return true;
            }
        }
        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!teams.contains(team) || team == null) { throw new IllegalArgumentException("Illegal Argument"); }
        ArrayList<String> subsetR = new ArrayList<>();
        int x = this.teams.indexOf(team);
        if (isTriviallyElimination(x)) {
            for (String s : teams) {
                int i = teams.indexOf(s);
                if (w[x] + r[x] < w[i]) {
                    subsetR.add(s);
                }
            }
        } else {
            FlowNetwork flowNetwork = constructFlowNetwork(x);
            FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, nTeams, nTeams+1);
            for (String s : teams) {
                int i = teams.indexOf(s);
                if (fordFulkerson.inCut(i)) {
                    subsetR.add(teams.get(i));
                }
            }
        }

        if (subsetR.isEmpty()) {
            return null;
        }
        return subsetR;
    }

//    public static void main(String[] args) {
//        BaseballElimination baseballElimination = new BaseballElimination("//home/yiclzh/Downloads/baseball/teams5.txt");
//        System.out.println(baseballElimination.numberOfTeams());
//        System.out.println(baseballElimination.teams());
//        System.out.println(baseballElimination.against("Boston", "New_York"));
//        System.out.println(baseballElimination.losses("Toronto"));
//        System.out.println(baseballElimination.wins("Baltimore"));
//        System.out.println(baseballElimination.remaining("Detroit"));
//        System.out.println(baseballElimination.isEliminated("Toronto"));
//        System.out.println(baseballElimination.isEliminated("Detroit"));
//        System.out.println(baseballElimination.isEliminated("New_York"));
//        System.out.println(baseballElimination.isEliminated("Boston"));
//    }
}
