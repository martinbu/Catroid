/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2014 The Catrobat Team
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
package org.catrobat.catroid.devices.arduino;

import android.content.Context;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;
import java.util.Arrays;
import java.util.UUID;

public class ArduinoImpl implements Arduino {

	private static final UUID ARDUINO_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private static final String TAG = ArduinoImpl.class.getSimpleName();

	protected ArduinoConnection arduinoConnection;
	protected Context context;

	private boolean isInitialized = false;


	public ArduinoImpl(Context applicationContext) {
		this.context = applicationContext;
	}

	@Override
	public String getName() {
		return "ARDUINO";
	}

	@Override
	public void setConnection(BluetoothConnection btConnection) {
		this.arduinoConnection = new ArduinoConnectionImpl(btConnection);
	}

	@Override
	public UUID getBluetoothDeviceUUID() {
		return ARDUINO_UUID;
	}

	@Override
	public Class<? extends BluetoothDevice> getDeviceType() {
		return BluetoothDevice.ARDUINO;
	}

	@Override
	public void disconnect() {
		if (arduinoConnection.isConnected()) {
			arduinoConnection.disconnect();
		}
	}

	@Override
	public boolean isAlive() {
		byte[] message = parseMessage("13");
		message[2] = 'D';

		try {
			arduinoConnection.sendAndReceive(message);
			return true;
		} catch (ArduinoException e) {
			return false;
		}
	}

	@Override
	public void initialise() {

		if (isInitialized) {
			return;
		}
		arduinoConnection.init();
		isInitialized = true;
	}

	@Override
	public void start() {
		initialise();
	}

	@Override
	public void pause() {}

	@Override
	public void destroy() {}

	@Override
	public void setDigitalArduinoPin(String digitalPinNumber, char pinValue) {
		byte[] message = parseMessage(digitalPinNumber);
		message[2] = (byte) pinValue;

		try {
			arduinoConnection.send(message);
		} catch (ArduinoException e) {
		}
	}

	@Override
	public double getDigitalArduinoPin(String digitalPinNumber) {
		byte[] message = parseMessage(digitalPinNumber);
		message[2] = 'D';

		try {
			byte[] receiveMessage = arduinoConnection.sendAndReceive(message);

			switch(receiveMessage[receiveMessage.length - 1]) {
				case 72:
					return 1.0;
				case 76:
					return 0.0;
				default:
					return -1.0;
			}
		} catch (ArduinoException e) {
			return -1;
		}
	}

	@Override
	public double getAnalogArduinoPin(String analogPinNumber) {
		byte[] message = parseMessage(analogPinNumber);
		message[2] = 'A';

		try {
			byte[] receiveMessage = arduinoConnection.sendAndReceive(message);
			byte[] value = Arrays.copyOfRange(receiveMessage, 3, receiveMessage.length);

			return (double) Float.valueOf(Arrays.toString(value));
		} catch (ArduinoException e) {
			return -1;
		}
	}

	@Override
	public void sendArduinoMessage(String arduinoMessage){
		byte[] byteMessage = new byte[arduinoMessage.length()];
		for(int i = 0; i < arduinoMessage.length(); i++)
		{

			byteMessage[i] = arduinoMessage.getBytes()[i];

		}
		try {
			arduinoConnection.send(byteMessage);
		} catch (ArduinoException e) {
		}
	}

	private byte[] parseMessage(String input)
	{
		byte[] message = new byte[3];
		if(input.length() != 2) {
			//exception here
			return message;
		}

		if(input.length() < 2) {
			message[0] = 0;
			message[1] = input.getBytes()[0];
		}
		else {
			message[0] = input.getBytes()[0];
			message[1] = input.getBytes()[1];
		}

		return message;
	}
}
