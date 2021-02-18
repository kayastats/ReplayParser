package kayastats;

import com.google.gson.Gson;
import com.google.protobuf.GeneratedMessage;
import skadistats.clarity.io.Util;
import skadistats.clarity.model.Entity;
import skadistats.clarity.model.FieldPath;
import skadistats.clarity.model.StringTable;
import skadistats.clarity.processor.entities.Entities;
import skadistats.clarity.processor.entities.OnEntityEntered;
import skadistats.clarity.processor.entities.OnEntityLeft;
import skadistats.clarity.processor.entities.UsesEntities;
import skadistats.clarity.processor.gameevents.OnCombatLogEntry;
import skadistats.clarity.processor.reader.OnMessage;
import skadistats.clarity.processor.reader.OnTickStart;
import skadistats.clarity.processor.runner.Context;
import skadistats.clarity.processor.runner.SimpleRunner;
import skadistats.clarity.model.CombatLogEntry;
import skadistats.clarity.processor.stringtables.StringTables;
import skadistats.clarity.processor.stringtables.UsesStringTable;
import skadistats.clarity.source.InputStreamSource;
import skadistats.clarity.wire.common.proto.Demo.CDemoFileInfo;
import skadistats.clarity.wire.common.proto.DotaUserMessages;
import skadistats.clarity.wire.common.proto.DotaUserMessages.CDOTAUserMsg_ChatEvent;
import skadistats.clarity.wire.common.proto.DotaUserMessages.CDOTAUserMsg_ChatWheel;
import skadistats.clarity.wire.common.proto.DotaUserMessages.CDOTAUserMsg_LocationPing;
import skadistats.clarity.wire.common.proto.DotaUserMessages.CDOTAUserMsg_SpectatorPlayerUnitOrders;
import skadistats.clarity.wire.common.proto.DotaUserMessages.DOTA_COMBATLOG_TYPES;
import skadistats.clarity.wire.s1.proto.S1UserMessages.CUserMsg_SayText2;
import skadistats.clarity.wire.s2.proto.S2UserMessages.CUserMessageSayText2;
import skadistats.clarity.wire.s2.proto.S2DotaGcCommon.CMsgDOTAMatch;

import java.util.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public class Parse {

    public class Entry {
        public Integer time = 0;
        public String type;
        public Integer team;
        public String unit;
        public String key;
        public Integer value;
        public Integer slot;
        public Integer player_slot;
        //chat event fields
        public Integer player1;
        public Integer player2;
        //combat log fields
        //public String attackername;
        //public String targetname;
        //public String sourcename;
        //public String targetsourcename;
        //public Boolean attackerhero;
        //public Boolean targethero;
        //public Boolean attackerillusion;
        //public Boolean targetillusion;
        //public Integer abilitylevel;
        //public String inflictor;
        //public Integer gold_reason;
        //public Integer xp_reason;
        //public String valuename;
        //public Float stun_duration;
        //public Float slow_duration;
        //entity fields
        public Integer gold;
        public Integer lh;
        public Integer xp;
        public Integer x;
        public Integer y;
        //public Integer z;
        public Float stuns;
        public Integer hero_id;
        //public transient List<Item> hero_inventory;
        //public Integer itemslot;
        //public Integer charges;
        //public Integer secondary_charges;
        //public Integer life_state;
        public Integer level;
        public Integer kills;
        public Integer deaths;
        public Integer assists;
        public Integer denies;
        //public Boolean entityleft;
        //public Integer ehandle;
        public Integer obs_placed;
        public Integer sen_placed;
        public Integer creeps_stacked;
        public Integer camps_stacked;
        public Integer rune_pickups;
        //public Boolean repicked;
        //public Boolean randomed;
        //public Boolean pred_vict;
        public Float stun_duration;
        public Float slow_duration;
        public Boolean tracked_death;
        //public Integer greevils_greed_stack;
        //public String tracked_sourcename;
        //public Integer firstblood_claimed;
        public Float teamfight_participation;
        public Integer towers_killed;
        public Integer roshans_killed;
        public Integer observers_placed;
        //public Integer draft_order;
        //public Boolean pick;
        //public Integer draft_active_team;
        //public Integer draft_extime0;
        //public Integer draft_extime1;

        public Entry() {
        }

        public Entry(Integer time) {
            this.time = time;
        }
    }

    public class StatsEntry {
        public Integer time;

        public Integer player_r1_hero_id;
        public Integer player_r1_net_worth;
        public Integer player_r1_experience;
        public Integer player_r1_kills;
        public Integer player_r1_deaths;
        public Integer player_r1_assists;
        public Integer player_r1_last_hits;
        public Integer player_r1_denies;
        public Integer player_r1_damage_dealt;
        public Integer player_r1_gpm;
        public Integer player_r1_xpm;
        public Integer player_r1_wards_placed;
        public Integer player_r1_wards_destroyed;

        public Integer player_r2_hero_id;
        public Integer player_r2_net_worth;
        public Integer player_r2_experience;
        public Integer player_r2_kills;
        public Integer player_r2_deaths;
        public Integer player_r2_assists;
        public Integer player_r2_last_hits;
        public Integer player_r2_denies;
        public Integer player_r2_damage_dealt;
        public Integer player_r2_gpm;
        public Integer player_r2_xpm;
        public Integer player_r2_wards_placed;
        public Integer player_r2_wards_destroyed;

        public Integer player_r3_hero_id;
        public Integer player_r3_net_worth;
        public Integer player_r3_experience;
        public Integer player_r3_kills;
        public Integer player_r3_deaths;
        public Integer player_r3_assists;
        public Integer player_r3_last_hits;
        public Integer player_r3_denies;
        public Integer player_r3_damage_dealt;
        public Integer player_r3_gpm;
        public Integer player_r3_xpm;
        public Integer player_r3_wards_placed;
        public Integer player_r3_wards_destroyed;

        public Integer player_r4_hero_id;
        public Integer player_r4_net_worth;
        public Integer player_r4_experience;
        public Integer player_r4_kills;
        public Integer player_r4_deaths;
        public Integer player_r4_assists;
        public Integer player_r4_last_hits;
        public Integer player_r4_denies;
        public Integer player_r4_damage_dealt;
        public Integer player_r4_gpm;
        public Integer player_r4_xpm;
        public Integer player_r4_wards_placed;
        public Integer player_r4_wards_destroyed;

        public Integer player_r5_hero_id;
        public Integer player_r5_net_worth;
        public Integer player_r5_experience;
        public Integer player_r5_kills;
        public Integer player_r5_deaths;
        public Integer player_r5_assists;
        public Integer player_r5_last_hits;
        public Integer player_r5_denies;
        public Integer player_r5_damage_dealt;
        public Integer player_r5_gpm;
        public Integer player_r5_xpm;
        public Integer player_r5_wards_placed;
        public Integer player_r5_wards_destroyed;

        public Integer player_d1_hero_id;
        public Integer player_d1_net_worth;
        public Integer player_d1_experience;
        public Integer player_d1_kills;
        public Integer player_d1_deaths;
        public Integer player_d1_assists;
        public Integer player_d1_last_hits;
        public Integer player_d1_denies;
        public Integer player_d1_damage_dealt;
        public Integer player_d1_gpm;
        public Integer player_d1_xpm;
        public Integer player_d1_wards_placed;
        public Integer player_d1_wards_destroyed;

        public Integer player_d2_hero_id;
        public Integer player_d2_net_worth;
        public Integer player_d2_experience;
        public Integer player_d2_kills;
        public Integer player_d2_deaths;
        public Integer player_d2_assists;
        public Integer player_d2_last_hits;
        public Integer player_d2_denies;
        public Integer player_d2_damage_dealt;
        public Integer player_d2_gpm;
        public Integer player_d2_xpm;
        public Integer player_d2_wards_placed;
        public Integer player_d2_wards_destroyed;

        public Integer player_d3_hero_id;
        public Integer player_d3_net_worth;
        public Integer player_d3_experience;
        public Integer player_d3_kills;
        public Integer player_d3_deaths;
        public Integer player_d3_assists;
        public Integer player_d3_last_hits;
        public Integer player_d3_denies;
        public Integer player_d3_damage_dealt;
        public Integer player_d3_gpm;
        public Integer player_d3_xpm;
        public Integer player_d3_wards_placed;
        public Integer player_d3_wards_destroyed;

        public Integer player_d4_hero_id;
        public Integer player_d4_net_worth;
        public Integer player_d4_experience;
        public Integer player_d4_kills;
        public Integer player_d4_deaths;
        public Integer player_d4_assists;
        public Integer player_d4_last_hits;
        public Integer player_d4_denies;
        public Integer player_d4_damage_dealt;
        public Integer player_d4_gpm;
        public Integer player_d4_xpm;
        public Integer player_d4_wards_placed;
        public Integer player_d4_wards_destroyed;

        public Integer player_d5_hero_id;
        public Integer player_d5_net_worth;
        public Integer player_d5_experience;
        public Integer player_d5_kills;
        public Integer player_d5_deaths;
        public Integer player_d5_assists;
        public Integer player_d5_last_hits;
        public Integer player_d5_denies;
        public Integer player_d5_damage_dealt;
        public Integer player_d5_gpm;
        public Integer player_d5_xpm;
        public Integer player_d5_wards_placed;
        public Integer player_d5_wards_destroyed;

        public Integer team_radiant_towers_killed = 0;
        public Integer team_dire_towers_killed = 0;

    }

    float INTERVAL = 30;
    float nextInterval = 0;
    Integer time = 0;
    int numPlayers = 10;
    int[] validIndices = new int[numPlayers];
    boolean init = false;
    int gameStartTime = 0;
    boolean postGame = false; // true when ancient destroyed
    private Gson g = new Gson();
    HashMap<String, Integer> name_to_slot = new HashMap<String, Integer>();
    HashMap<String, Integer> abilities_tracking = new HashMap<String, Integer>();
    HashMap<Integer, Integer> slot_to_playerslot = new HashMap<Integer, Integer>();
    HashMap<Long, Integer> steamid_to_playerslot = new HashMap<Long, Integer>();
    HashMap<Integer, Integer> cosmeticsMap = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> dotaplusxpMap = new HashMap<Integer, Integer>(); // playerslot, xp
    HashMap<Integer, Integer> ward_ehandle_to_slot = new HashMap<Integer, Integer>();
    private ArrayList<Boolean> isPlayerStartingItemsWritten;
    int pingCount = 0;
    private ArrayList<StatsEntry> logBuffer = new ArrayList<StatsEntry>();

    //Draft stage variable
    boolean[] draftOrderProcessed = new boolean[22];
    int order = 1;
    boolean isDraftStartTimeProcessed = false; //flag to know if draft start time is already handled

    boolean isDotaPlusProcessed = false;

    InputStream is = null;
    OutputStream os = null;

    public Parse(InputStream input, OutputStream output) throws IOException
    {
        is = input;
        os = output;
        long tStart = System.currentTimeMillis();
        new SimpleRunner(new InputStreamSource(is)).runWith(this);
        long tMatch = System.currentTimeMillis() - tStart;
        System.err.format("total time taken: %s\n", (tMatch) / 1000.0);
    }

    public void output(StatsEntry e) {
        try {
            if (gameStartTime == 0) {
                logBuffer.add(e);
            } else {
                e.time -= gameStartTime;
                this.os.write((g.toJson(e) + "\n").getBytes());
            }
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
    }
/*
    public void flushLogBuffer() {
        for (Entry e : logBuffer) {
            output(e);
        }
        logBuffer = null;
    }
*/
    @OnCombatLogEntry
    public void onCombatLogEntry(Context ctx, CombatLogEntry cle) {
        try
        {
            time = Math.round(cle.getTimestamp());
            //create a new entry
            Entry combatLogEntry = new Entry(time);
            combatLogEntry.type = cle.getType().name();
            combatLogEntry.value = cle.getValue();

            if (combatLogEntry.type.equals("DOTA_COMBATLOG_GAME_STATE") && combatLogEntry.value == 6) {
                postGame = true;
            }
            if (combatLogEntry.type.equals("DOTA_COMBATLOG_GAME_STATE") && combatLogEntry.value == 5) {
                //alternate to combat log for getting game zero time (looks like this is set at the same time as the game start, so it's not any better for streaming)
                // int currGameStartTime = Math.round( (float) grp.getProperty("m_pGameRules.m_flGameStartTime"));
                if (gameStartTime == 0) {
                    gameStartTime = combatLogEntry.time;
                    nextInterval = gameStartTime + 60;
                    //flushLogBuffer();
                }
            }
            /*if (cle.getType().ordinal() <= 19) {
                output(combatLogEntry);
            }*/
        }
        catch(Exception e)
        {
            System.err.println(e);
            System.err.println(cle);
        }
    }

    @UsesStringTable("EntityNames")
    @UsesEntities
    @OnTickStart
    public void onTickStart(Context ctx, boolean synthetic) {

        //TODO check engine to decide whether to use s1 or s2 entities
        //ctx.getEngineType()

        //s1 DT_DOTAGameRulesProxy
        Entity grp = ctx.getProcessor(Entities.class).getByDtName("CDOTAGamerulesProxy");
        Entity pr = ctx.getProcessor(Entities.class).getByDtName("CDOTA_PlayerResource");
        Entity dData = ctx.getProcessor(Entities.class).getByDtName("CDOTA_DataDire");
        Entity rData = ctx.getProcessor(Entities.class).getByDtName("CDOTA_DataRadiant");

        // Create draftStage variable
        Integer draftStage = getEntityProperty(grp, "m_pGameRules.m_nGameState", null);

        if (pr != null)
        {
            //Radiant coach shows up in vecPlayerTeamData as position 5
            //all the remaining dire entities are offset by 1 and so we miss reading the last one and don't get data for the first dire player
            //coaches appear to be on team 1, radiant is 2 and dire is 3?
            //construct an array of valid indices to get vecPlayerTeamData from
            if (!init)
            {
                int added = 0;
                int i = 0;
                //according to @Decoud Valve seems to have fixed this issue and players should be in first 10 slots again
                //sanity check of i to prevent infinite loop when <10 players?
                while (added < numPlayers && i < 100) {
                    try
                    {
                        //check each m_vecPlayerData to ensure the player's team is radiant or dire
                        int playerTeam = getEntityProperty(pr, "m_vecPlayerData.%i.m_iPlayerTeam", i);
                        int teamSlot = getEntityProperty(pr, "m_vecPlayerTeamData.%i.m_iTeamSlot", i);
                        Long steamid = getEntityProperty(pr, "m_vecPlayerData.%i.m_iPlayerSteamID", i);
                        //System.err.format("%s %s %s: %s\n", i, playerTeam, teamSlot, steamid);
                        if (playerTeam == 2 || playerTeam == 3) {
                            //output the player_slot based on team and teamslot
                            Entry entry = new Entry(time);
                            entry.type = "player_slot";
                            entry.key = String.valueOf(added);
                            entry.value = (playerTeam == 2 ? 0 : 128) + teamSlot;
                            //output(entry);
                            //add it to validIndices, add 1 to added
                            validIndices[added] = i;
                            added += 1;
                            slot_to_playerslot.put(added, entry.value);
                            steamid_to_playerslot.put(steamid, entry.value);
                        }
                    }
                    catch(Exception e)
                    {
                        //swallow the exception when an unexpected number of players (!=10)
                        //System.err.println(e);
                    }

                    i += 1;
                }
                init = true;
            }

            if (!postGame && time >= nextInterval)
            {
                StatsEntry stats = new StatsEntry();
                stats.time = time;
                //System.err.println(pr);
                for (int i = 0; i < numPlayers; i++)
                {
                    Integer hero = getEntityProperty(pr, "m_vecPlayerTeamData.%i.m_nSelectedHeroID", validIndices[i]);
                    int handle = getEntityProperty(pr, "m_vecPlayerTeamData.%i.m_hSelectedHero", validIndices[i]);
                    int playerTeam = getEntityProperty(pr, "m_vecPlayerData.%i.m_iPlayerTeam", validIndices[i]);
                    int teamSlot = getEntityProperty(pr, "m_vecPlayerTeamData.%i.m_iTeamSlot", validIndices[i]);

                    //2 is radiant, 3 is dire, 1 is other?
                    Entity dataTeam = playerTeam == 2 ? rData : dData;

                    Entry entry = new Entry(time);
                    entry.type = "interval";
                    entry.slot = i;
                    entry.teamfight_participation = getEntityProperty(pr, "m_vecPlayerTeamData.%i.m_flTeamFightParticipation", validIndices[i]);;
                    entry.level = getEntityProperty(pr, "m_vecPlayerTeamData.%i.m_iLevel", validIndices[i]);
                    entry.kills = getEntityProperty(pr, "m_vecPlayerTeamData.%i.m_iKills", validIndices[i]);
                    entry.deaths = getEntityProperty(pr, "m_vecPlayerTeamData.%i.m_iDeaths", validIndices[i]);
                    entry.assists = getEntityProperty(pr, "m_vecPlayerTeamData.%i.m_iAssists", validIndices[i]);
                    entry.denies = getEntityProperty(dataTeam, "m_vecDataTeam.%i.m_iDenyCount", teamSlot);
                    entry.obs_placed = getEntityProperty(dataTeam, "m_vecDataTeam.%i.m_iObserverWardsPlaced", teamSlot);
                    entry.sen_placed = getEntityProperty(dataTeam, "m_vecDataTeam.%i.m_iSentryWardsPlaced", teamSlot);
                    entry.creeps_stacked = getEntityProperty(dataTeam, "m_vecDataTeam.%i.m_iCreepsStacked", teamSlot);
                    entry.camps_stacked = getEntityProperty(dataTeam, "m_vecDataTeam.%i.m_iCampsStacked", teamSlot);
                    entry.rune_pickups = getEntityProperty(dataTeam, "m_vecDataTeam.%i.m_iRunePickups", teamSlot);
                    entry.towers_killed = getEntityProperty(dataTeam, "m_vecDataTeam.%i.m_iTowerKills", teamSlot);
                    entry.roshans_killed = getEntityProperty(dataTeam, "m_vecDataTeam.%i.m_iRoshanKills", teamSlot);
                    entry.observers_placed = getEntityProperty(dataTeam, "m_vecDataTeam.%i.m_iObserverWardsPlaced", teamSlot);

                    if (teamSlot >= 0)
                    {
                        entry.gold = getEntityProperty(dataTeam, "m_vecDataTeam.%i.m_iTotalEarnedGold", teamSlot);
                        entry.lh = getEntityProperty(dataTeam, "m_vecDataTeam.%i.m_iLastHitCount", teamSlot);
                        entry.xp = getEntityProperty(dataTeam, "m_vecDataTeam.%i.m_iTotalEarnedXP", teamSlot);
                        entry.stuns = getEntityProperty(dataTeam, "m_vecDataTeam.%i.m_fStuns", teamSlot);
                    }

                    //TODO: gem, rapier time?
                    //need to dump inventory items for each player and possibly keep track of item entity handles

                    //get the player's hero entity
                    Entity e = ctx.getProcessor(Entities.class).getByHandle(handle);
                    //get the hero's coordinates
                    if (e != null)
                    {
                        //System.err.println(e);
                        entry.x = getEntityProperty(e, "CBodyComponent.m_cellX", null);
                        entry.y = getEntityProperty(e, "CBodyComponent.m_cellY", null);
                        //System.err.format("%s, %s\n", entry.x, entry.y);
                        //get the hero's entity name, ex: CDOTA_Hero_Zuus
                        entry.unit = e.getDtClass().getDtName();
                        entry.hero_id = hero;
                        //check if hero has been assigned to entity
                        if (hero > 0)
                        {
                            //get the hero's entity name, ex: CDOTA_Hero_Zuus
                            String unit = e.getDtClass().getDtName();
                            //grab the end of the name, lowercase it
                            String ending = unit.substring("CDOTA_Unit_Hero_".length());
                            //valve is bad at consistency and the combat log name could involve replacing camelCase with _ or not!
                            //double map it so we can look up both cases
                            String combatLogName = "npc_dota_hero_" + ending.toLowerCase();
                            //don't include final underscore here since the first letter is always capitalized and will be converted to underscore
                            String combatLogName2 = "npc_dota_hero" + ending.replaceAll("([A-Z])", "_$1").toLowerCase();
                            //System.err.format("%s, %s, %s\n", unit, combatLogName, combatLogName2);
                            //populate for combat log mapping
                            name_to_slot.put(combatLogName, entry.slot);
                            name_to_slot.put(combatLogName2, entry.slot);

                        }
                    }

                    if (entry.slot == 0) {
                        stats.player_r1_hero_id = entry.hero_id;
                        stats.player_r1_net_worth = entry.gold; // TODO: check if it is correct
                        stats.player_r1_experience = entry.xp;
                        stats.player_r1_kills = entry.kills;
                        stats.player_r1_deaths = entry.deaths;
                        stats.player_r1_assists = entry.assists;
                        stats.player_r1_last_hits = entry.lh;
                        stats.player_r1_denies = entry.denies;
                        stats.player_r1_damage_dealt = -1; // TODO: check how we can get that info
                        stats.player_r1_gpm = -1; // TODO: check how we can get that info
                        stats.player_r1_xpm = -1; // TODO: check how we can get that info
                        stats.player_r1_wards_placed = entry.obs_placed + entry.sen_placed;
                        stats.player_r1_wards_destroyed = -1; // TODO: check how we can get that info

                        stats.team_radiant_towers_killed += entry.towers_killed;
                    }
                    if (entry.slot == 1) {
                        stats.player_r2_hero_id = entry.hero_id;
                        stats.player_r2_net_worth = entry.gold; // TODO: check if it is correct
                        stats.player_r2_experience = entry.xp;
                        stats.player_r2_kills = entry.kills;
                        stats.player_r2_deaths = entry.deaths;
                        stats.player_r2_assists = entry.assists;
                        stats.player_r2_last_hits = entry.lh;
                        stats.player_r2_denies = entry.denies;
                        stats.player_r2_damage_dealt = -1; // TODO: check how we can get that info
                        stats.player_r2_gpm = -1; // TODO: check how we can get that info
                        stats.player_r2_xpm = -1; // TODO: check how we can get that info
                        stats.player_r2_wards_placed = entry.obs_placed + entry.sen_placed;
                        stats.player_r2_wards_destroyed = -1; // TODO: check how we can get that info

                        stats.team_radiant_towers_killed += entry.towers_killed;
                    }
                    if (entry.slot == 2) {
                        stats.player_r3_hero_id = entry.hero_id;
                        stats.player_r3_net_worth = entry.gold; // TODO: check if it is correct
                        stats.player_r3_experience = entry.xp;
                        stats.player_r3_kills = entry.kills;
                        stats.player_r3_deaths = entry.deaths;
                        stats.player_r3_assists = entry.assists;
                        stats.player_r3_last_hits = entry.lh;
                        stats.player_r3_denies = entry.denies;
                        stats.player_r3_damage_dealt = -1; // TODO: check how we can get that info
                        stats.player_r3_gpm = -1; // TODO: check how we can get that info
                        stats.player_r3_xpm = -1; // TODO: check how we can get that info
                        stats.player_r3_wards_placed = entry.obs_placed + entry.sen_placed;
                        stats.player_r3_wards_destroyed = -1; // TODO: check how we can get that info

                        stats.team_radiant_towers_killed += entry.towers_killed;
                    }
                    if (entry.slot == 3) {
                        stats.player_r4_hero_id = entry.hero_id;
                        stats.player_r4_net_worth = entry.gold; // TODO: check if it is correct
                        stats.player_r4_experience = entry.xp;
                        stats.player_r4_kills = entry.kills;
                        stats.player_r4_deaths = entry.deaths;
                        stats.player_r4_assists = entry.assists;
                        stats.player_r4_last_hits = entry.lh;
                        stats.player_r4_denies = entry.denies;
                        stats.player_r4_damage_dealt = -1; // TODO: check how we can get that info
                        stats.player_r4_gpm = -1; // TODO: check how we can get that info
                        stats.player_r4_xpm = -1; // TODO: check how we can get that info
                        stats.player_r4_wards_placed = entry.obs_placed + entry.sen_placed;
                        stats.player_r4_wards_destroyed = -1; // TODO: check how we can get that info

                        stats.team_radiant_towers_killed += entry.towers_killed;
                    }
                    if (entry.slot == 4) {
                        stats.player_r5_hero_id = entry.hero_id;
                        stats.player_r5_net_worth = entry.gold; // TODO: check if it is correct
                        stats.player_r5_experience = entry.xp;
                        stats.player_r5_kills = entry.kills;
                        stats.player_r5_deaths = entry.deaths;
                        stats.player_r5_assists = entry.assists;
                        stats.player_r5_last_hits = entry.lh;
                        stats.player_r5_denies = entry.denies;
                        stats.player_r5_damage_dealt = -1; // TODO: check how we can get that info
                        stats.player_r5_gpm = -1; // TODO: check how we can get that info
                        stats.player_r5_xpm = -1; // TODO: check how we can get that info
                        stats.player_r5_wards_placed = entry.obs_placed + entry.sen_placed;
                        stats.player_r5_wards_destroyed = -1; // TODO: check how we can get that info

                        stats.team_radiant_towers_killed += entry.towers_killed;
                    }
                    if (entry.slot == 5) {
                        stats.player_d1_hero_id = entry.hero_id;
                        stats.player_d1_net_worth = entry.gold; // TODO: check if it is correct
                        stats.player_d1_experience = entry.xp;
                        stats.player_d1_kills = entry.kills;
                        stats.player_d1_deaths = entry.deaths;
                        stats.player_d1_assists = entry.assists;
                        stats.player_d1_last_hits = entry.lh;
                        stats.player_d1_denies = entry.denies;
                        stats.player_d1_damage_dealt = -1; // TODO: check how we can get that info
                        stats.player_d1_gpm = -1; // TODO: check how we can get that info
                        stats.player_d1_xpm = -1; // TODO: check how we can get that info
                        stats.player_d1_wards_placed = entry.obs_placed + entry.sen_placed;
                        stats.player_d1_wards_destroyed = -1; // TODO: check how we can get that info

                        stats.team_dire_towers_killed += entry.towers_killed;
                    }
                    if (entry.slot == 6) {
                        stats.player_d2_hero_id = entry.hero_id;
                        stats.player_d2_net_worth = entry.gold; // TODO: check if it is correct
                        stats.player_d2_experience = entry.xp;
                        stats.player_d2_kills = entry.kills;
                        stats.player_d2_deaths = entry.deaths;
                        stats.player_d2_assists = entry.assists;
                        stats.player_d2_last_hits = entry.lh;
                        stats.player_d2_denies = entry.denies;
                        stats.player_d2_damage_dealt = -1; // TODO: check how we can get that info
                        stats.player_d2_gpm = -1; // TODO: check how we can get that info
                        stats.player_d2_xpm = -1; // TODO: check how we can get that info
                        stats.player_d2_wards_placed = entry.obs_placed + entry.sen_placed;
                        stats.player_d2_wards_destroyed = -1; // TODO: check how we can get that info

                        stats.team_dire_towers_killed += entry.towers_killed;
                    }
                    if (entry.slot == 7) {
                        stats.player_d3_hero_id = entry.hero_id;
                        stats.player_d3_net_worth = entry.gold; // TODO: check if it is correct
                        stats.player_d3_experience = entry.xp;
                        stats.player_d3_kills = entry.kills;
                        stats.player_d3_deaths = entry.deaths;
                        stats.player_d3_assists = entry.assists;
                        stats.player_d3_last_hits = entry.lh;
                        stats.player_d3_denies = entry.denies;
                        stats.player_d3_damage_dealt = -1; // TODO: check how we can get that info
                        stats.player_d3_gpm = -1; // TODO: check how we can get that info
                        stats.player_d3_xpm = -1; // TODO: check how we can get that info
                        stats.player_d3_wards_placed = entry.obs_placed + entry.sen_placed;
                        stats.player_d3_wards_destroyed = -1; // TODO: check how we can get that info

                        stats.team_dire_towers_killed += entry.towers_killed;
                    }
                    if (entry.slot == 8) {
                        stats.player_d4_hero_id = entry.hero_id;
                        stats.player_d4_net_worth = entry.gold; // TODO: check if it is correct
                        stats.player_d4_experience = entry.xp;
                        stats.player_d4_kills = entry.kills;
                        stats.player_d4_deaths = entry.deaths;
                        stats.player_d4_assists = entry.assists;
                        stats.player_d4_last_hits = entry.lh;
                        stats.player_d4_denies = entry.denies;
                        stats.player_d4_damage_dealt = -1; // TODO: check how we can get that info
                        stats.player_d4_gpm = -1; // TODO: check how we can get that info
                        stats.player_d4_xpm = -1; // TODO: check how we can get that info
                        stats.player_d4_wards_placed = entry.obs_placed + entry.sen_placed;
                        stats.player_d4_wards_destroyed = -1; // TODO: check how we can get that info

                        stats.team_dire_towers_killed += entry.towers_killed;
                    }
                    if (entry.slot == 9) {
                        stats.player_d5_hero_id = entry.hero_id;
                        stats.player_d5_net_worth = entry.gold; // TODO: check if it is correct
                        stats.player_d5_experience = entry.xp;
                        stats.player_d5_kills = entry.kills;
                        stats.player_d5_deaths = entry.deaths;
                        stats.player_d5_assists = entry.assists;
                        stats.player_d5_last_hits = entry.lh;
                        stats.player_d5_denies = entry.denies;
                        stats.player_d5_damage_dealt = -1; // TODO: check how we can get that info
                        stats.player_d5_gpm = -1; // TODO: check how we can get that info
                        stats.player_d5_xpm = -1; // TODO: check how we can get that info
                        stats.player_d5_wards_placed = entry.obs_placed + entry.sen_placed;
                        stats.player_d5_wards_destroyed = -1; // TODO: check how we can get that info

                        stats.team_dire_towers_killed += entry.towers_killed;
                    }
                }
                output(stats);
                nextInterval += INTERVAL;
            }
        }
    }

    public <T> T getEntityProperty(Entity e, String property, Integer idx) {
        try {
            if (e == null) {
                return null;
            }
            if (idx != null) {
                property = property.replace("%i", Util.arrayIdxToString(idx));
            }
            FieldPath fp = e.getDtClass().getFieldPathForName(property);
            return e.getPropertyForFieldPath(fp);
        }
        catch (Exception ex) {
            return null;
        }
    }

}
