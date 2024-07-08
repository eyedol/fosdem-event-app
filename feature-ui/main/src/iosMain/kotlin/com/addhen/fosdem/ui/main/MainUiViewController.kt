// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.main

import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.core.api.i18n.AppStrings
import com.addhen.fosdem.core.api.screens.SessionsScreen
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.datetime.Instant
import kotlinx.datetime.toNSDate
import me.tatarka.inject.annotations.Inject
import platform.EventKit.EKEntityType
import platform.EventKit.EKEvent
import platform.EventKit.EKEventStore
import platform.EventKitUI.EKEventEditViewAction
import platform.EventKitUI.EKEventEditViewController
import platform.EventKitUI.EKEventEditViewDelegateProtocol
import platform.Foundation.NSAttributedString
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.dataUsingEncoding
import platform.SafariServices.SFSafariViewController
import platform.UIKit.NSCharacterEncodingDocumentAttribute
import platform.UIKit.NSDocumentTypeDocumentAttribute
import platform.UIKit.NSHTMLTextDocumentType
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleCancel
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString
import platform.UIKit.UIViewController
import platform.UIKit.create
import platform.darwin.DISPATCH_TIME_NOW
import platform.darwin.NSEC_PER_MSEC
import platform.darwin.NSObject
import platform.darwin.dispatch_after
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_time

typealias MainUiViewController = () -> UIViewController

@Inject
@Suppress("ktlint:standard:function-naming")
fun MainUiViewController(
  mainContent: MainContent,
): UIViewController = ComposeUIViewController {
  val backstack = rememberSaveableBackStack(listOf(SessionsScreen))
  val navigator = rememberCircuitNavigator(backstack, onRootPop = { /* no-op */ })
  val uiViewController = LocalUIViewController.current
  val appStrings = LocalStrings.current

  mainContent.Content(
    backstack,
    navigator,
    { url ->
      val safari = SFSafariViewController(NSURL(string = url))
      uiViewController.presentViewController(safari, animated = true, completion = null)
    },
    { info -> uiViewController.shareInfo(info) },
    { title, room, description, startAtMillSeconds, endAtMillSeconds ->
      uiViewController.saveEventToCalendar(
        appStrings,
        title,
        room,
        description,
        startAtMillSeconds,
        endAtMillSeconds,
      )
    },
    Modifier,
  )
}

private fun UIViewController.shareInfo(shareInfo: String) {
  if (shareInfo.isEmpty()) return
  val parsedHtmlInfo = parseHtml(shareInfo)
  val activityViewController = UIActivityViewController(
    activityItems = listOf(parsedHtmlInfo),
    applicationActivities = null,
  )
  presentViewController(activityViewController, animated = true, completion = null)
}

private fun UIViewController.saveEventToCalendar(
  appStrings: AppStrings,
  title: String,
  room: String,
  description: String,
  startAtMillSeconds: Long,
  endAtMillSeconds: Long,
) {
  val eventStore = EKEventStore()
  eventStore.requestAccessToEntityType(EKEntityType.EKEntityTypeEvent) { granted, _ ->
    afterTimeout(1) {
      if (granted) {
        val eventEditViewController = EKEventEditViewController().apply {
          editViewDelegate = object : NSObject(), EKEventEditViewDelegateProtocol {
            override fun eventEditViewController(
              controller: EKEventEditViewController,
              didCompleteWithAction: EKEventEditViewAction,
            ) {
              controller.dismissViewControllerAnimated(true, null)
            }
          }
          this.eventStore = eventStore
          val event = EKEvent.eventWithEventStore(eventStore).apply {
            this.title = title
            this.location = room
            this.notes = parseHtml(description)
            this.startDate = Instant.fromEpochMilliseconds(startAtMillSeconds).toNSDate()
            this.endDate = Instant.fromEpochMilliseconds(endAtMillSeconds).toNSDate()
          }
          this.event = event
        }
        eventEditViewController.eventStore = eventStore
        presentViewController(eventEditViewController, true, null)
      } else {
        val settingsUrl = NSURL(string = UIApplicationOpenSettingsURLString)
        val alert = UIAlertController.alertControllerWithTitle(
          title = appStrings.calendarPermissionDeniedTitle,
          message = appStrings.calendarPermissionDeniedMessage,
          preferredStyle = UIAlertControllerStyleAlert,
        )
        alert.addAction(
          UIAlertAction.actionWithTitle(
            appStrings.settingsTitle,
            style = UIAlertActionStyleDefault,
          ) {
            UIApplication.sharedApplication.openURL(settingsUrl)
          },
        )
        alert.addAction(
          UIAlertAction.actionWithTitle(
            appStrings.settingsCancelButton,
            style = UIAlertActionStyleCancel,
            handler = null,
          ),
        )
        presentModalViewController(alert, true)
      }
    }
  }
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
private fun parseHtml(html: String): String {
  val data = (html as NSString).dataUsingEncoding(NSUTF8StringEncoding) ?: return html
  val options: Map<Any?, *> = mapOf(
    NSDocumentTypeDocumentAttribute to NSHTMLTextDocumentType,
    NSCharacterEncodingDocumentAttribute to NSUTF8StringEncoding,
  )
  val attributedString =
    NSAttributedString.create(
      data = data,
      options = options,
      documentAttributes = null,
      error = null,
    )
  return attributedString?.string ?: html
}

internal inline fun afterTimeout(
  milliseconds: Long,
  crossinline action: () -> Unit,
): () -> Unit {
  var stillRun = true
  dispatch_after(
    dispatch_time(
      DISPATCH_TIME_NOW,
      milliseconds * NSEC_PER_MSEC.toLong(),
    ),
    dispatch_get_main_queue(),
  ) {
    if (!stillRun) return@dispatch_after
    action()
  }
  return { stillRun = false }
}
