package com.example.myapplication.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BeforeAfterSlider(
    beforeImageUrl: String,
    afterImageUrl: String,
    modifier: Modifier = Modifier,
    beforeLabel: String = "Before",
    afterLabel: String = "After"
) {
    var sliderPosition by remember { mutableFloatStateOf(0.5f) }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        sliderPosition = (offset.x / size.width).coerceIn(0f, 1f)
                    }
                ) { change, dragAmount ->
                    change.consume()
                    sliderPosition = (sliderPosition + dragAmount.x / size.width).coerceIn(0f, 1f)
                }
            }
    ) {
        val fullWidth = this.maxWidth
        val sliderOffset = fullWidth * sliderPosition
        val lineOffset = (sliderOffset - 1.dp).coerceAtLeast(0.dp)
        val knobOffset = (sliderOffset - 16.dp).coerceIn(0.dp, fullWidth - 32.dp)

        AsyncProjectImage(
            imageUrl = afterImageUrl,
            contentDescription = afterLabel,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(sliderOffset)
                    .clip(RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp))
        ) {
            AsyncProjectImage(
                imageUrl = beforeImageUrl,
                contentDescription = beforeLabel,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(fullWidth)
            )
        }

        Box(
            modifier = Modifier
                .offset(x = lineOffset)
                .fillMaxHeight()
                .width(2.dp)
                .background(Color.White.copy(alpha = 0.9f))
        )

        Surface(
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 6.dp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = knobOffset)
                .width(32.dp)
                .height(32.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val path = Path().apply {
                        moveTo(size.width * 0.34f, size.height * 0.5f)
                        lineTo(size.width * 0.47f, size.height * 0.36f)
                        moveTo(size.width * 0.34f, size.height * 0.5f)
                        lineTo(size.width * 0.47f, size.height * 0.64f)

                        moveTo(size.width * 0.66f, size.height * 0.5f)
                        lineTo(size.width * 0.53f, size.height * 0.36f)
                        moveTo(size.width * 0.66f, size.height * 0.5f)
                        lineTo(size.width * 0.53f, size.height * 0.64f)
                    }
                    drawPath(
                        path = path,
                        color = Color.Gray,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f)
                    )
                }
            }
        }

        Text(
            text = beforeLabel,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
                .background(Color.Black.copy(alpha = 0.45f), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )

        Text(
            text = afterLabel,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
                .background(Color.Black.copy(alpha = 0.45f), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
