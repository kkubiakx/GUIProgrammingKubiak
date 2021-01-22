using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class BtnSceneSelect : MonoBehaviour
{
    public void loadMain()
    {
        SceneManager.LoadScene("CenterScreenMain 1");
    }

    public void loadRadio()
    {
        SceneManager.LoadScene("Radio");
    }

    public void loadNav()
    {
        SceneManager.LoadScene("Nav");
    }

    public void loadAC()
    {
        SceneManager.LoadScene("AC");
    }

    public void loadCruise()
    {
        SceneManager.LoadScene("Cruise");
    }

    public void loadSettings()
    {
        SceneManager.LoadScene("Settings");
    }

    public void loadSeats()
    {
        SceneManager.LoadScene("Seats");
    }

    public void loadGauge()
    {
        SceneManager.LoadScene("GaugeScreen");
    }

    public void exit()
	{
        Application.Quit();
    }
}
