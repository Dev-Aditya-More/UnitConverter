package com.example.unitconverterrr
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverterrr.ui.theme.UnitConverterrrTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterrrTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()

                }

            }
        }
    }
}

enum class Unit(val displayName: String, val factor: Double) {
    METER("Meters", 1.0),
    CENTIMETER("Centimeters", 0.01),
    FEET("Feet", 0.3048),
    MILLIMETER("Millimeters", 0.001)
}

@Composable
fun UnitConverter() {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf(Unit.METER) }
    var outputUnit by remember { mutableStateOf(Unit.METER) }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    var isDarkMode by remember { mutableStateOf(false) }

    val customTextStyle = androidx.compose.ui.text.TextStyle(
        fontFamily = FontFamily(
            Font(R.font.bree_serif, FontWeight.Bold, FontStyle.Normal)
        ),

    )
    // Define dark and light color schemes
    val darkColors = darkColorScheme(
        primary = Color.White,
        background = Color.Black,
        surface = Color.DarkGray,
        onPrimary = Color.Black,
        onBackground = Color.White,
        onSurface = Color.White
    )

    val lightColors = lightColorScheme(
        primary = Color.Black,
        background = Color.White,
        surface = Color.LightGray,
        onPrimary = Color.White,
        onBackground = Color.Black,
        onSurface = Color.Black
    )
    val colors = if (isDarkMode) darkColors else lightColors

    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result =
            (inputValueDouble * inputUnit.factor * 100.0 / outputUnit.factor).roundToInt() / 100.0
        outputValue = result.toString()
    }

    MaterialTheme(colorScheme = colors) {
        Column(
            modifier = Modifier
                .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Unit converter",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = if(isDarkMode) Color.White else Color.Black
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            //Toggle button Mode
            Button(onClick = { isDarkMode = !isDarkMode }) {
                if (isDarkMode) {
                    Image(
                        painter = painterResource(id = R.drawable.light_mode_24dp_5f6368_fill0_wght400_grad0_opsz24),
                        contentDescription = "Light Mode"
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.dark_mode_24dp_5f6368_fill0_wght400_grad0_opsz24),
                        contentDescription = "Dark Mode",
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = inputValue,
                onValueChange = {
                    inputValue = it
                    convertUnits()
                },
                label = { Text("Enter Value") },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Box {
                    Button(onClick = { iExpanded = true }) {
                        Text(text = inputUnit.displayName)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                    }
                    DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                        Unit.entries.forEach { unit ->
                            DropdownMenuItem(
                                text = { Text(unit.displayName) },
                                onClick = {
                                    iExpanded = false
                                    inputUnit = unit
                                    convertUnits()
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Box {
                    Button(onClick = { oExpanded = true }) {
                        Text(text = outputUnit.displayName)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                    }
                    DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                        Unit.entries.forEach { unit ->
                            DropdownMenuItem(
                                text = { Text(unit.displayName) },
                                onClick = {
                                    oExpanded = false
                                    outputUnit = unit
                                    convertUnits()
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Result: $outputValue ${outputUnit.displayName}",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = if (isDarkMode) Color.White else Color.Black
                )
            )
        }
    }
}
@Preview
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}