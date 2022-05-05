package com.example.myapplication;

import com.google.gson.annotations.SerializedName;
import java.util.List;


public class Ladestation
{
    /*===== ATTRIBUTES =====*/
    /* General information */
    public int id;
    private String operator;
    private String street;
    private String number;
    private String additional;
    private int postal_code;
    private String location;
    private String state;
    private String area;
    private float lat;
    private float lon;
    private String installation_date;
    private float conn_power;
    private ModuleType module_type;
    private int number_of_connections;

    /* Charging Point 1 */
    private List<PlugType> plug_types_1;
    private float power_1;
    private String public_key_1;

    /* Charging Point 2 */
    private List<PlugType> plug_types_2;
    private float power_2;
    private String public_key_2;

    /* Charging Point 3 */
    private List<PlugType> plug_types_3;
    private float power_3;
    private String public_key_3;

    /* Charging Point 4 */
    private List<PlugType> plug_types_4;
    private float power_4;
    private String public_key_4;


    /*===== GETTERS =====*/

    /**
     * Getter for operator
     * @return operator
     */
    public String getOperator() {return operator;}

    /**
     * Getter for Street
     * @return street
     */
    public String getStreet() {return street;}

    /**
     * Getter for number
     * @return number
     */
    public String getNumber() {return number;}

    /**
     * Getter for additional
     * @return additional
     */
    public String getAdditional() {return additional;}

    /**
     * Getter for postal_code
     * @return postal_code
     */
    public int getPostalCode() {return postal_code;}

    /**
     * Getter for location
     * @return location
     */
    public String getLocation() {return location;}

    /**
     * Getter for location
     * @return location
     */
    public String getState() {return location;}

    /**
     * Getter for area
     * @return area
     */
    public String getArea() {return area;}

    /**
     * Getter for lat
     * @return lat
     */
    public float getLat() {return lat;}

    /**
     * Getter for lon
     * @return lon
     */
    public float getLon() {return lon;}

    /**
     * Getter for installation_date
     * @return installation_date
     */
    public String getInstallationDate() {return installation_date;}

    /**
     * Getter for conn_power
     * @return conn_power
     */
    public float getConnPower() {return conn_power;}

    /**
     * Getter for module_type
     * @return module_type
     */
    public ModuleType getModuleType() {return module_type;}

    /**
     * Getter for number_of_connections
     * @return number_of_connections
     */
    public int getNumberOfConnections() {return number_of_connections;}

    /**
     * Getter for plug_types_1
     * @return plug_types_1
     */
    public List<PlugType> getPlugTypes_1() {return plug_types_1;}

    /**
     * Getter for power_1
     * @return power_1
     */
    public float getPower_1() {return power_1;}

    /**
     * Getter for public_key_1
     * @return public_key_1
     */
    public String getPublicKey_1() {return public_key_1;}

    /**
     * Getter for plug_types_2
     * @return plug_types_2
     */
    public List<PlugType> getPlugTypes_2() {return plug_types_2;}

    /**
     * Getter for power_2
     * @return power_2
     */
    public float getPower_2() {return power_2;}

    /**
     * Getter for public_key_2
     * @return public_key_2
     */
    public String getPublicKey_2() {return public_key_2;}

    /**
     * Getter for plug_types_3
     * @return plug_types_3
     */
    public List<PlugType> getPlugTypes_3() {return plug_types_3;}

    /**
     * Getter for power_3
     * @return power_3
     */
    public float getPower_3() {return power_3;}

    /**
     * Getter for public_key_3
     * @return public_key_3
     */
    public String getPublicKey_3() {return public_key_3;}

    /**
     * Getter for plug_types_4
     * @return plug_types_4
     */
    public List<PlugType> getPlugTypes_4() {return plug_types_4;}

    /**
     * Getter for power_4
     * @return power_4
     */
    public float getPower_4() {return power_4;}

    /**
     * Getter for public_key_4
     * @return public_key_4
     */
    public String getPublicKey_4() {return public_key_4;}


    /*===== ENUMS =====*/
    public enum PlugType
    {
        @SerializedName("AC Steckdose Typ 2") AC_PLUG_TYPE_2,
        @SerializedName("AC Kupplung Typ 2") AC_CLUTCH_TYPE_2,
        @SerializedName("AC Schuko") AC_SCHUKO,
        @SerializedName("DC Kupplung Combo") DC_CLUTCH_COMBO,
        @SerializedName("DC CHAdeMO") DC_CHADEMO
    }

    public enum ModuleType
    {
        @SerializedName("Normalladeeinrichtung") STANDARD,
        @SerializedName("Schnellladeeinrichtung") FAST_CHARGING
    }
}
