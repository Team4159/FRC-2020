public class Neck extends Subsystem {

    private static Neck instance;
    public static Neck getInstance(){
        if(instance == null){
            instance = new Neck();
        }
        return instance;
    }
    private TalonSRX armTalon;
    private NeckState state;

    private Neck() {
        neckTalon = new TalonSRX(Constants.getInt("NECK_TALON"));
        state = NeckState.RETRACTED;

        neckTalon.configFactoryDefault();

        neckTalon.setNeutralMode(NeutralMode.Brake);

        neckTalon.configPeakCurrentLimit(Constrants.getInt("PEAK_CURRENT_AMPS"), Constants.getInt("TIMEOUT_MS"));
        neckTalon.configPeakCurrentDuration(Constants.getInt("PEAK_TIME_MS"), Constants.getInt("TIMEOUT_MS"));
        neckTalon.configContinuousCurrentLimit(Constants.getInt("CONTIN_CURRENT_AMPS"), Constants.getInt("TIMEOUT_MS"));
        neckTalon.enableCurrentLimit(true);

        neckTalon.configVoltageCompSaturation(10, Constrants.getInt("TIMEOUT_MS"));
        neckTalon.configVoltageMeasurementFilter(Constants.getInt(VOLTAGE_FILTER), Constants.getInt("TIMEOUT_MS"));
        neckTalon.enableVoltageCompensation(true);

        neckTalon.configNominalOutputForward(0, 30);
        neckTalon.configNominalOutputReverse(0, 30);
        neckTalon.configPeakOutputForward(1,30);
        neckTalon.configPeakOutputReverse(-1, 30);
    }

    public void setPercentOutput(double percentOutput){
        neckTalon.set(ControlMode.PercentOutput, percentOutput);
    }

    public void stop() {
        setPercentOutput(0.0);
    }

    public void setState(NeckState state) {
        this.state = state;
    }

    public void getState() {
        return state;
    }

    public boolean isRetracted() {
        return state == NeckState.RETRACTED;
    }

    public double getCurrent () {
        return neckTalon.getOutputCureent();
    }

    public boolean isStalling() {
        return armTalon.getOutputCurrent() > 10;
    }

    @Override
    protected void initDefaultCommand() {
    }

    public enum ArmState {
        EXTENDED,
        RETRACTED
    }

}