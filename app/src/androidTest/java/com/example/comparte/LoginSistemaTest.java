package com.example.comparte;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.containsString;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.comparte.activities.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Prueba funcional del login.
 * Verifica que al introducir credenciales válidas, el usuario accede a MainActivity
 * y se muestra el fragmento correspondiente según el rol.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginSistemaTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void loginExitoso_navegaAMainActivity() {
        // Introducir email válido
        onView(withId(R.id.emailEditText))
                .perform(typeText("a@gmail.com"), closeSoftKeyboard());

        // Introducir contraseña válida
        onView(withId(R.id.passwordEditText))
                .perform(typeText("1234"), closeSoftKeyboard());

        // Pulsar botón de login
        onView(withId(R.id.btnLoginIniciar)).perform(click());

        // Verificar que se navega correctamente: comprobamos si aparece alguno de los textos esperados
        onView(anyOf(
                withText(containsString("Inquilino")),
                withText(containsString("Propietario")),
                withText(containsString("Admin")),
                withText(containsString("Bienvenido"))
        )).check(matches(isDisplayed()));
    }
}
