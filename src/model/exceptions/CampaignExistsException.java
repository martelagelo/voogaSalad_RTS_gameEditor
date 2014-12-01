package model.exceptions;

/**
 * 
 * @author Jonathan Tseng
 *
 */
public class CampaignExistsException extends DescribableStateExistsException {

    /**
     * Auto-generated
     */
    private static final long serialVersionUID = 6345214561381502176L;

    private static final String DESCRIBABLE_STATE_TYPE = "Campaign";

    public CampaignExistsException (String campaignName) {
        super(DESCRIBABLE_STATE_TYPE, campaignName);
    }

}
