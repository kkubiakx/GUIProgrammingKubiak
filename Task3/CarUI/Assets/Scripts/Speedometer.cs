using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Speedometer : MonoBehaviour
{
	private const float MAX_SPEED_ANGLE = -270;
	private const float ZERO_SPEED_ANGLE = 0;

	public Transform needleTransform;

	public Text percentage;

	public Text mileage;

	private float speedMax;
	private float speed;
	public float batteryPrc;

	private void Awake()
	{
		speed = 0f;
		speedMax = 270f;
		batteryPrc = 100f;
	}

	private void Update()
	{
		HandlePlayerInput();

		//speed += 10f * Time.deltaTime;
		//if (speed > speedMax) speed = speedMax;

		needleTransform.eulerAngles = new Vector3(0, 0, GetSpeedRotation());
		percentage.text = batteryPrc.ToString("0");
		mileage.text = (batteryPrc * 5).ToString("0");
	}

	private void HandlePlayerInput()
	{
		if (Input.GetKey(KeyCode.UpArrow) && batteryPrc > 0)
		{
			float acceleration = 60f;
			speed += acceleration * Time.deltaTime;
			batteryPrc -= speed / 1000;
		}
		else
		{
			float deceleration = 20f;
			speed -= deceleration * Time.deltaTime;
			batteryPrc += speed / 10000;
		}

		if (Input.GetKey(KeyCode.DownArrow))
		{
			float brakeSpeed = 100f;
			speed -= brakeSpeed * Time.deltaTime;
			batteryPrc += speed / 10000;
		}

		speed = Mathf.Clamp(speed, 0f, speedMax);
		batteryPrc = Mathf.Clamp(batteryPrc, 0f, 100);
	}

	private float GetSpeedRotation()
	{
		float totalAngleSize = ZERO_SPEED_ANGLE - MAX_SPEED_ANGLE;

		float speedNormalized = speed / speedMax;

		return ZERO_SPEED_ANGLE - speedNormalized * totalAngleSize;
	}

}
