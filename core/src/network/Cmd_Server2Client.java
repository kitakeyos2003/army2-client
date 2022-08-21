package network;

public class Cmd_Server2Client {

    public static final short LOGIN_SUCESS = 3;
    public static final short LOGIN_FAIL = 4;
    public static final short CHAT_TO = 5;
    public static final short ROOM_LIST = 6;
    public static final short BOARD_LIST = 7;
    public static final short JOINBOARD_SUCCESS = 8;
    public static final short JOINBOARD_FAIL = 17;
    public static final short SOMEONE_JOINBOARD = 12;
    public static final short SOMEONE_LEAVEBOARD = 14;
    public static final short SOMEONE_READY = 16;
    public static final short SET_MONEY = 19;
    public static final short CHAT_FROM_BOARD = 9;
    public static final short SET_MONEY_ERROR = 10;
    public static final short KICK = 11;
    public static final short DRAW = 25;
    public static final short DENY_DRAW = 26;
    public static final short RICHEST_LIST = 31;
    public static final short PLAYER_DETAIL = 34;
    public static final short FRIENDLIST = 29;
    public static final short SEARCH = 36;
    public static final short ADD_FRIEND_RESULT = 32;
    public static final short DELETE_FRIEND_RESULT = 33;
    public static final short GAME_RESULT = 37;
    public static final short USER_DATA = 40;
    public static final short PING = 42;
    public static final short BUY_AVATAR_SUCCESS = 43;
    public static final short SERVER_MESSAGE = 45;
    public static final short SERVER_INFO = 46;
    public static final short VERSION = 48;
    public static final short ADMIN_COMMAND_RESPONSE = 47;
    public static final short STOP_GAME = 50;
    public static final short FINISH = 51;
    public static final short BONUS_MONEY = 52;
    public static final short START_ARMY = 20;
    public static final short MOVE_ARMY = 21;
    public static final short FIRE_ARMY = 22;
    public static final short SHOOT_RESULT = 23;
    public static final short NEXT_TURN = 24;
    public static final short SKIP = 64;
    public static final short WIND = 25;
    public static final short USE_ITEM = 26;
    public static final short DIE = 60;
    public static final short UPDATE_HP = 51;
    public static final short UPDATE_XY = 53;
    public static final short RANDOM_ITEM = 59;
    public static final short CHOOSE_ITEM = 68;
    public static final short CHOOSE_GUN = 69;
    public static final short CHOOSE_MAP = 70;
    public static final short CHANGE_TEAM = 71;
    public static final short BUY_ITEM = 72;
    public static final short CHANGE_MODE = 73;
    public static final short BUY_GUN = 74;
    public static final short NHAN_SMS_DIALOG = 63;
    public static final short ANTI_HACK_MESS = 64;
    public static final short MAP_SELECT = 75;
    public static final byte AUTO_BOARD = 76;
    public static final short FIND_PLAYER = 78;
    public static final byte CHANGE_PASS = 81;
    public static final byte ORBIT = 82;
    public static final byte FIRE_TRAINING = 84;
    public static final byte OPEN_LINK = 86;
    public static final byte ROOM_CAPTION = 88;
    public static final byte GET_BOSS = 89;
    public static final byte GET_FILEPACK = 90;
    public static final byte SUB_FILEPACK_1 = 0;
    public static final byte SUB_FILEPACK_2 = 1;
    public static final byte SUB_FILEPACK_3 = 2;
    public static final byte SUB_FILEPACK_4 = 3;
    public static final byte SUB_FILEPACK_5 = 4;
    public static final byte SUB_FILEPACK_6 = 5;
    public static final byte UNDESTROYTILE = 92;
    public static final byte FLY = 93;
    public static final byte CAPTURE = 95;
    public static final byte BIT = 96;
    public static final byte UPDATE_EXP = 97;
    public static final byte CHARACTOR_INFO = 99;
    public static final byte LUCKY = 100;
    public static final byte INVENTORY = 101;
    public static final byte CHANGE_EQUIP = 102;
    public static final byte SHOP_EQUIP = 103;
    public static final byte BUY_EQUIP = 104;
    public static final byte UPDATE_MONEY = 105;
    public static final byte EYE_SMOKE = 106;
    public static final byte FREEZE = 107;
    public static final byte POISON = 108;
    public static final byte TIME_BOMB = 109;
    public static final byte RULET = 110;
    public static final byte TEST = 111;
    public static final byte ITEM_SLOT = 112;
    public static final byte ANGRY = 113;
    public static final byte VERSION_CODE = 114;
    public static final byte CLAN_ICON = 115;
    public static final byte TOP_CLAN = 116;
    public static final byte CLAN_INFO = 117;
    public static final byte CLAN_MEMBER = 118;
    public static final byte GIFT = 119;
    public static final byte GET_BIG_IMAGE = 120;
    public static final byte GET_BIG_EQUIP_HD = -120;
    public static final byte REGISTER_2 = 121;
    public static final byte CHARGE_MONEY_2 = 122;
    public static final byte CHAT_TEAM = 123;
    public static final byte GHOST_BIT = 124;
    public static final byte MATERIAL = 125;
    public static final byte MATERIAL_ICON = 126;
    public static final byte IMBUE = 17;
    public static final byte INVENTORY_UPDATE = 27;
    public static final byte END_INVISIBLE = 80;
    public static final byte VAMPIRE = 59;
    public static final byte SHOP_LINHTINH = -3;
    public static final byte TEST_2 = -5;
    public static final byte TRAINING_MAP = -6;
    public static final byte CURR_EQUIP_DBKEY = -7;
    public static final byte ITEM_NUM = -10;
    public static final byte SIGN_OUT = -4;
    public static final byte SHOP_BIETDOI = -12;
    public static final byte BANGTHANHTICH = -14;
    public static final byte GET_LUCKYGIFT = -17;
    public static final byte FOMULA = -18;
    public static final byte CHANGE_ROOM_NAME = -19;
    public static final byte CLAN_MONEY = -21;
    public static final byte UPDATE_CLANMONEY = -22;
    public static final byte MISSISON = -23;
    public static final byte CUP = -24;
    public static final byte GET_MORE_DAY = -25;
    public static final byte MORE_GAME = -100;
    public static final byte GET_AGENT_PROVIDER = -26;
    public static final byte NEW_ROOMLIST = -28;
    public static final byte REG3 = -93;
    public static final byte CHARGE_MONEY_3 = -94;
    public static final byte REG_NICK_FREE = -101;
    public static final byte CHANGE_REQUEST = -103;
    public static final byte IN_APP_PURCHASE = -102;
}
