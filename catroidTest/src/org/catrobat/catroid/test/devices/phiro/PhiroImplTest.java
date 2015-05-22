/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2015 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.test.devices.phiro;

import android.content.Context;
import android.test.AndroidTestCase;

import org.catrobat.catroid.common.bluetooth.ConnectionDataLogger;
import org.catrobat.catroid.devices.arduino.common.firmata.BytesHelper;
import org.catrobat.catroid.devices.arduino.phiro.Phiro;
import org.catrobat.catroid.devices.arduino.phiro.PhiroImpl;

public class PhiroImplTest extends AndroidTestCase {

	private Phiro phiro;
	private ConnectionDataLogger logger;

	private static final int PIN_SPEAKER_OUT = 3;

	private static final int PIN_RGB_RED_LEFT = 4;
	private static final int PIN_RGB_GREEN_LEFT = 5;
	private static final int PIN_RGB_BLUE_LEFT = 6;

	private static final int PIN_RGB_RED_RIGHT = 7;
	private static final int PIN_RGB_GREEN_RIGHT = 8;
	private static final int PIN_RGB_BLUE_RIGHT = 9;

	private static final int PIN_LEFT_MOTOR_BACKWARD = 10;
	private static final int PIN_LEFT_MOTOR_FORWARD = 11;

	private static final int PIN_RIGHT_MOTOR_FORWARD = 12;
	private static final int PIN_RIGHT_MOTOR_BACKWARD = 13;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		phiro = new PhiroImpl();
		logger = ConnectionDataLogger.createLocalConnectionLogger();
		phiro.setConnection(logger.getConnectionProxy());
		phiro.initialise();
	}

	@Override
	protected void tearDown() throws Exception {
		phiro.disconnect();
		logger.disconnect();
		super.tearDown();
	}

	private static final int SPEED = 42;
	private static final byte ANALOG_MESSAGE = (byte)0xE0;

	public void testMoveLeftMotorForward() {
		phiro.moveLeftMotorForward(SPEED);
		testSpeed(SPEED, PIN_LEFT_MOTOR_FORWARD);
	}

	private void testSpeed(int speed, int pin) {
		assertEquals("Exspected analog command", ANALOG_MESSAGE | BytesHelper.encodeChannel(pin), logger.getNextSentMessage());
		assertEquals("Exspected analog command", BytesHelper.lsb(speed), logger.getNextSentMessage());
		assertEquals("Exspected analog command", BytesHelper.msb(speed), logger.getNextSentMessage());
	}

	public void testMoveLeftMotorBackward(int speed) {

	}

	public void testMoveRightMotorForward(int speed) {

	}

	public void testMoveRightMotorBackward(int speed) {

	}

	public void testStopLeftMotor() {

	}

	public void testStopRightMotor() {

	}

	public void testStopAllMovements() {

	}




}
