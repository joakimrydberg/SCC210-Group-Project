package interfaces;

import game.Button;

/**
 * Implement this to react to click events on buttons and other clickable classes
 *
 * @see Clickable
 * @author josh
 * @date 15/02/16.
 */
public interface ClickListener {

     void buttonClicked(Clickable button, Object[] args);
}
