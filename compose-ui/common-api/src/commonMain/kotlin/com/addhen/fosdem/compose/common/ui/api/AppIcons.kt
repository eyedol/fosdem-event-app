package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object AppIcons {
  object Filled
}

val AppIcons.Filled.CalendarAddOn: ImageVector
  get() {
    if (_calendarAddOn != null) {
      return _calendarAddOn!!
    }
    _calendarAddOn = Builder(name = "Filled.CalendarAddOn", defaultWidth = 24.0.dp, defaultHeight =
    24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
      group {
        path(fill = SolidColor(Color(0xFF404944)), stroke = null, strokeLineWidth = 0.0f,
          strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
          pathFillType = NonZero) {
          moveTo(18.0f, 22.0f)
          curveTo(17.448f, 22.0f, 17.0f, 21.552f, 17.0f, 21.0f)
          verticalLineTo(19.0f)
          horizontalLineTo(15.0f)
          curveTo(14.448f, 19.0f, 14.0f, 18.552f, 14.0f, 18.0f)
          curveTo(14.0f, 17.448f, 14.448f, 17.0f, 15.0f, 17.0f)
          horizontalLineTo(17.0f)
          verticalLineTo(15.0f)
          curveTo(17.0f, 14.448f, 17.448f, 14.0f, 18.0f, 14.0f)
          curveTo(18.552f, 14.0f, 19.0f, 14.448f, 19.0f, 15.0f)
          verticalLineTo(17.0f)
          horizontalLineTo(21.0f)
          curveTo(21.552f, 17.0f, 22.0f, 17.448f, 22.0f, 18.0f)
          curveTo(22.0f, 18.552f, 21.552f, 19.0f, 21.0f, 19.0f)
          horizontalLineTo(19.0f)
          verticalLineTo(21.0f)
          curveTo(19.0f, 21.552f, 18.552f, 22.0f, 18.0f, 22.0f)
          close()
          moveTo(5.0f, 20.0f)
          curveTo(4.45f, 20.0f, 3.979f, 19.804f, 3.588f, 19.413f)
          curveTo(3.196f, 19.021f, 3.0f, 18.55f, 3.0f, 18.0f)
          verticalLineTo(6.0f)
          curveTo(3.0f, 5.45f, 3.196f, 4.979f, 3.588f, 4.588f)
          curveTo(3.979f, 4.196f, 4.45f, 4.0f, 5.0f, 4.0f)
          horizontalLineTo(6.0f)
          verticalLineTo(3.0f)
          curveTo(6.0f, 2.448f, 6.448f, 2.0f, 7.0f, 2.0f)
          curveTo(7.552f, 2.0f, 8.0f, 2.448f, 8.0f, 3.0f)
          verticalLineTo(4.0f)
          horizontalLineTo(14.0f)
          verticalLineTo(3.0f)
          curveTo(14.0f, 2.448f, 14.448f, 2.0f, 15.0f, 2.0f)
          curveTo(15.552f, 2.0f, 16.0f, 2.448f, 16.0f, 3.0f)
          verticalLineTo(4.0f)
          horizontalLineTo(17.0f)
          curveTo(17.55f, 4.0f, 18.021f, 4.196f, 18.413f, 4.588f)
          curveTo(18.804f, 4.979f, 19.0f, 5.45f, 19.0f, 6.0f)
          verticalLineTo(12.1f)
          curveTo(18.667f, 12.05f, 18.333f, 12.025f, 18.0f, 12.025f)
          curveTo(17.667f, 12.025f, 17.333f, 12.05f, 17.0f, 12.1f)
          verticalLineTo(10.0f)
          horizontalLineTo(5.0f)
          verticalLineTo(18.0f)
          horizontalLineTo(12.0f)
          curveTo(12.0f, 18.333f, 12.025f, 18.667f, 12.075f, 19.0f)
          curveTo(12.125f, 19.333f, 12.217f, 19.667f, 12.35f, 20.0f)
          horizontalLineTo(5.0f)
          close()
          moveTo(5.0f, 8.0f)
          horizontalLineTo(17.0f)
          verticalLineTo(6.0f)
          horizontalLineTo(5.0f)
          verticalLineTo(8.0f)
          close()
        }
      }
    }
      .build()
    return _calendarAddOn!!
  }

private var _calendarAddOn: ImageVector? = null
