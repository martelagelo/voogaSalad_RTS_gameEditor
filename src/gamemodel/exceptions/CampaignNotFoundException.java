package gamemodel.exceptions;

/**
 * 
 * @author Jonathan Tseng
 *
 */
public class CampaignNotFoundException extends DescribableStateExistsException {

    /**
     * Auto-generated
     */
    private static final long serialVersionUID = 5167073529475643443L;

    private static final String DESCRIBABLE_STATE_TYPE = "Campaign";

    public CampaignNotFoundException (String campaignName) {
        super(DESCRIBABLE_STATE_TYPE, campaignName);
    }

}
