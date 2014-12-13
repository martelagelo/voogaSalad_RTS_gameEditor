package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import model.data.WizardData;
import model.data.WizardDataType;
import model.data.WizardType;
import engine.visuals.Dimension;
import engine.visuals.elementVisuals.animations.AnimationSequence;
import engine.visuals.elementVisuals.animations.AnimationTag;
import engine.visuals.elementVisuals.animations.AnimatorState;


/**
 * Factory that creates a SavableGameElementState based on the info args
 * 
 * @author Nishad Agrawal, Jonathan Tseng
 *
 */
public class AnimatorStateFactory {

    public static AnimatorState createAnimatorState (WizardData data) {

        Dimension dim = new Dimension(Integer.parseInt(data.getValueByKey(WizardDataType.FRAME_X)),
                                      Integer.parseInt(data.getValueByKey(WizardDataType.FRAME_Y)));

        Set<AnimationSequence> sequences = createAnimationSequences(data);
        AnimatorState anim =
                new AnimatorState(data.getValueByKey(WizardDataType.IMAGE),
                                  data.getValueByKey(WizardDataType.COLOR_MASK),
                                  dim,
                                  Integer.parseInt(data.getValueByKey(WizardDataType.ROWS)),
                                  sequences);

        return anim;
    }

    private static Set<AnimationSequence> createAnimationSequences (WizardData data) {
        Set<AnimationSequence> sequences = new HashSet<>();
        for (WizardData animationData : data.getWizardDataByType(WizardType.ANIMATION_SEQUENCE)) {
            List<AnimationTag> tags = createAnimationTags(animationData);
            int start = Integer.parseInt(animationData.getValueByKey(WizardDataType.START_FRAME));
            int stop = Integer.parseInt(animationData.getValueByKey(WizardDataType.STOP_FRAME)) + 1;
            boolean repeats =
                    Boolean.parseBoolean(animationData
                            .getValueByKey(WizardDataType.ANIMATION_REPEAT));
            double slowness =
                    Double.parseDouble(animationData
                            .getValueByKey(WizardDataType.SLOWNESS_MULTIPLIER));
            AnimationSequence sequence =
                    new AnimationSequence(tags, start, stop, repeats, slowness);
            sequences.add(sequence);
        }
        return sequences;
    }

    private static List<AnimationTag> createAnimationTags (WizardData animationData) {
        return Arrays.asList(animationData.getValueByKey(WizardDataType.ANIMATION_TAG)
                .split(","))
                .stream().map(tag -> AnimationTag.valueOf(tag))
                .collect(Collectors.toList());
    }

}
