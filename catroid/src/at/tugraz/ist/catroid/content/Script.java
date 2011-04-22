/**
 *  Catroid: An on-device graphical programming language for Android devices
 *  Copyright (C) 2010  Catroid development team 
 *  (<http://code.google.com/p/catroid/wiki/Credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.tugraz.ist.catroid.content;

import java.io.Serializable;
import java.util.ArrayList;

import at.tugraz.ist.catroid.content.bricks.Brick;
import at.tugraz.ist.catroid.exception.InterruptedRuntimeException;

public class Script implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Brick> brickList;
	private boolean isTouchScript;
	private boolean isFinished;
	private boolean paused;
	private int brickPositionAfterPause;
	private String name;
	private Sprite sprite;

	public Script(String name, Sprite sprite) {
		this.name = name;
		brickList = new ArrayList<Brick>();
		paused = false;
		isFinished = false;
		brickPositionAfterPause = 0;
		this.sprite = sprite;
		setTouchScript(false);
	}

	public void run() {
		if (isFinished && !isTouchScript) {
			return;
		}
		isFinished = false;
		for (int i = brickPositionAfterPause; i < brickList.size(); i++) {
			if (paused) {
				brickPositionAfterPause = i;
				return;
			}
			try {
				brickList.get(i).execute();
				sprite.setToDraw(true);
			} catch (InterruptedRuntimeException e) { //Brick was interrupted
				brickPositionAfterPause = i;
				return;
			}
		}
		isFinished = true;
		brickPositionAfterPause = 0;
	}

	public void addBrick(Brick brick) {
		brickList.add(brick);
	}

	public void removeBrick(Brick brick) {
		brickList.remove(brick);
	}

	public void moveBrickBySteps(Brick brick, int steps) {
		int oldIndex = brickList.indexOf(brick);
		int newIndex;

		if (steps < 0) {
			newIndex = oldIndex + steps < 0 ? 0 : oldIndex + steps;
			brickList.remove(oldIndex);
			brickList.add(newIndex, brick);
		} else if (steps > 0) {
			newIndex = oldIndex + steps >= brickList.size() ? brickList.size() - 1 : oldIndex + steps;
			brickList.remove(oldIndex);
			brickList.add(newIndex, brick);
		} else {
			return;
		}
	}

	public ArrayList<Brick> getBrickList() {
		return brickList;
	}

	public void setTouchScript(boolean isTouchScript) {
		this.isTouchScript = isTouchScript;
		if (isTouchScript) {
			this.isFinished = true;
		}
	}

	public boolean isTouchScript() {
		return isTouchScript;
	}

	public synchronized void setPaused(boolean paused) {
		this.paused = paused;
	}

	public boolean isPaused() {
		return paused;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Sprite getSprite() {
		return sprite;
	}
}