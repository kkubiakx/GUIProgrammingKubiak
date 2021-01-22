using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class TempControl : MonoBehaviour
{
    public Text leftTemp;
    public Text rightTemp;

    public bool turnedOn;

    void Start()
	{
        turnedOn = false;
	}

    public void togglePower()
	{
        turnedOn = !turnedOn;
	}

    public void incLeft()
	{
        if(float.Parse(leftTemp.text) < 25.0 && turnedOn) leftTemp.text = (float.Parse(leftTemp.text) + 0.5f).ToString("0.0");
    }

    public void decLeft()
    {
        if (float.Parse(leftTemp.text) > 16.0 && turnedOn) leftTemp.text = (float.Parse(leftTemp.text) - 0.5f).ToString("0.0");
    }


    public void incRight()
    {
        if (float.Parse(rightTemp.text) < 25.0 && turnedOn) rightTemp.text = (float.Parse(rightTemp.text) + 0.5f).ToString("0.0");
    }

    public void decRight()
    {
        if (float.Parse(rightTemp.text) > 16.0 && turnedOn) rightTemp.text = (float.Parse(rightTemp.text) - 0.5f).ToString("0.0");
    }



}
