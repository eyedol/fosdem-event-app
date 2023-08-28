package com.addhen.fosdem.data.events.database

import com.addhen.fosdem.data.sqldelight.api.entities.AttachmentEntity
import com.addhen.fosdem.data.sqldelight.api.entities.DayEntity
import com.addhen.fosdem.data.sqldelight.api.entities.EventEntity
import com.addhen.fosdem.data.sqldelight.api.entities.LinkEntity
import com.addhen.fosdem.data.sqldelight.api.entities.RoomEntity
import com.addhen.fosdem.data.sqldelight.api.entities.SpeakerEntity
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

val link = LinkEntity(
  id = 1,
  url = "https://video.fosdem.org/2023/D.matrix/cascaded_selective_forwarding_units.webm",
  text = "Video recording (WebM/VP9)"
)

val speaker = SpeakerEntity(
  id = 1,
  name = "FOSDEM Staff"
)

val attachment = AttachmentEntity(
  id = 1,
  type = "slides",
  url = "https://fosdem.org/2023/schedule/event/sds_vhost_user_blk/attachments/slides/5444/export/events/attachments/sds_vhost_user_blk/slides/5444/stefanha_fosdem_2023.pdf"
)

val link2 = LinkEntity(
  id = 2,
  url = "https://video.fosdem.org/2023/Janson/celebrating_25_years_of_open_source.webm",
  text = "Video recording (WebM/VP9, 101M)"
)

val speaker2 = SpeakerEntity(
  id = 2,
  name = "Nick Vidal"
)

val attachment2 = AttachmentEntity(
  id = 2,
  type = "slides",
  url = "https://fosdem.org/2023/schedule/event/sds_vhost_user_blk/attachments/slides/5444/export/events/attachments/sds_vhost_user_blk/slides/5444/stefanha_fosdem_2023.pdf"
)

val day = DayEntity(
  id = 1,
  date = LocalDate.parse("2023-02-04")
)

val room = RoomEntity(
  id = 1,
  name = "Janson"
)

val day2 = DayEntity(
  id = 2,
  date = LocalDate.parse("2023-02-05")
)

val room2 = RoomEntity(
  id = 2,
  name = "Janson"
)



val day1Event = EventEntity(
  id = 1,
  start_time = LocalTime.parse("09:30"),
  duration = LocalTime.parse("00:25"),
  title = "Welcome to FOSDEM 2023",
  description = "Welcome to FOSDEM 2023!",
  isBookmarked = false,
  abstractText = "FOSDEM welcome and opening talk",
  day = day,
  links = listOf(link),
  speakers = listOf(speaker),
  room = room,
  track = "Keynotes",
  attachments = listOf(attachment)
)

val day2Event = EventEntity(
  id = 2,
  start_time = LocalTime.parse("11:40"),
  duration = LocalTime.parse("00:15"),
  title = "FOSSbot: An open source and open design educational robot",
  description = "Welcome to FOSDEM 2023!",
  isBookmarked = false,
  abstractText = "FOSSbot is a free and open source and open design 3D printed robot that can be used in education. It belongs to the DIY (Do It Yourself) category, with the logic that it can easily be built by other people besides its creators, and its disassembly and reassembly process can be part of an educational process in the classroom. This is possible since FOSSBot is made of electronic materials that can be easily found commercially at a low cost while the plastic parts are printable. It provides four main operation modes: i) a UI with buttons that is suitable for preschool children and shows the main capabilities of the robot, for example moving the robot forward or backward, or turning clockwise and anti-clockwise, ii) a block-based graphical programming interface (based on Google Blockly) aimed at primary school students, iii) a notebook coding interface that can be used for teaching high school students and elementary school students the fundamentals of Python programming (such as loops, conditions, events, etc.), iv) a Python coding mode, which allows to directly work into the FOSSBot programming shell and gives direct control to the low-level electronics.</p> <p>The software of FOSSbot is based on a modular stack that allows the implementation the various programming functions, orchestrates everything through the UI, and controls the hardware in an easy way through a software library that plays the role of the FOSSBot operating system. This stack includes Google Blockly, Python Jupyter, Python Flask which hosts FOSSBot's UI, the core FOSSBot library written in Python which controls the bot's hardware, and finally the manual operation it offers to users through a user interface, i.e. a way to control the robot without any programming knowledge. FOSSBot's software is built using the latest versions of the above technologies, including Docker, continuous integration, and microservices integration (CI) logic. As mentioned in the introduction, programming the robot can be done in different ways which are followed and analyzed.</p> <p>The hardware is based on a Raspberry-Pi zero that controls everything and a set of electronics comprising: - Sensors: Ultrasonic distance sensor, Battery Sensor, Accelerometer, Gyroscope, Odometers, IR Receiver, Line detection sensors, Light Sensors - Interaction Features: Speaker and Front RGB LED - General Features: Brick-compatible surface, Hole in the front for attaching a marker/pencil, a special pulling loop, rechargeable batteries</p> <p>All the plastic parts, except the wheels, are printable on a 3D printer and the total printing time does not exceed 36 hours. It is worth mentioning that the body of the robot has been designed in such a way as to facilitate its assembly process. This has been achieved since cases have been designed inside the robot, and adapted to the electronic components so that they are placed in the corresponding positions and do not move during the use of the robot. Also, on the outer surface, there are printed symbols that indicate the position of each sensor. The symbols also help teachers to know the position of the sensors, e.g. speaker, led, etc. The vertical tube that runs from top to bottom of the main body of the robot allows for the attachment of a pencil or marker so that by moving the FOSSBot around an area covered with paper, shapes can be created. Printed meshes on the front and top of the robot help keep electrical parts cool. The robot's charging port, on/off switch, and a unique loop for towing small items are all located on the back. The loop also serves to protect the robot from minor collisions. Two printed spoilers above the wheels add to wheel protection and to the robot's aesthetic design. The top surface of the robot is divided into two pieces. A cover that attaches to the main body using unique clips is the original accessory. The main cover can be joined to the top cover using an easy twist-and-lock function. It can be easily removed to give access to the interior of the robot and can also support a base of plastic bricks, allowing bricks to be added on top of the robot. This option enables lower-grade teachers to combine FOSSBot with other projects and can help add new activities to FOSSBot.</p> <p>Printing and assembly instructions and figures can be found in this manual: https://github.com/eellak/fossbot/blob/master/electronics<em>instructions/draft</em>instructions.pdf</p> <p>Educators' supporting material is under development.",
  day = day,
  links = listOf(link2),
  speakers = listOf(speaker2),
  room = room2,
  track = "Keynotes",
  attachments = listOf(attachment2)
)

val events = listOf(day1Event, day2Event)

