package us.xnetik.tv.ui.screens.access

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import coil3.compose.AsyncImage
import us.xnetik.tv.R

private val BgTop = Color(0xFF0D0F1C)
private val BgBot = Color(0xFF1A0A2E)
private val AccentPurple = Color(0xFFB48CFF)
private val BoxBorder = Color(0xFF3A2060)
private val BoxBg = Color(0xFF180D30)

@Composable
fun LockScreen(deviceId: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(BgTop, BgBot))
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(48.dp)
        ) {
            AsyncImage(
                model = R.drawable.app_logo_mark,
                contentDescription = null,
                modifier = Modifier.size(96.dp)
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "xnetik tv",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = "Access Inactive",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = AccentPurple,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Your subscription is not active or has expired.",
                fontSize = 16.sp,
                color = Color(0xFFBBBBBB),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = "Your Device ID",
                fontSize = 13.sp,
                color = Color(0xFF888888),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .widthIn(min = 240.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(BoxBg)
                    .border(1.dp, BoxBorder, RoundedCornerShape(12.dp))
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = deviceId,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 4.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Contact your provider with this Device ID\nto activate your subscription.",
                fontSize = 14.sp,
                color = Color(0xFF888888),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
        }
    }
}
