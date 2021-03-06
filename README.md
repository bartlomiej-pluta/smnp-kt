# Simple Music Notation Processor
SMNP is a command line tool aimed at doing music stuff using custom domain-specific language.
You are able to create music (both monophonic and polyphonic) using simple notation 
and then synthesize it with custom settings, plot the signal, evaluate DFT on it and so on.
Music tools can be used not only to create music but also to prepare a number of scenarios 
of ear training like recognizing intervals etc.
Apart from that developed domain-specific language offers you the tools known from most popular 
programming languages like conditional statements, loops, variable mechanism, 
functions (including defining custom ones) etc.
  
It is based on my [previous project](https://github.com/bartlomiej-pluta/Simple-Music-Notation-Processor), however, it is
totally rewritten in Kotlin with improved syntax, application architecture and some new features.
Please don't be surprised if a great part of the _README_ or documentation is 1:1 copied from the older _SMNP_ project.
The main purposes and general design are kept so there simply is no need to change these parts.

## For what?
You may ask whether this kind of tool including design of custom language isn't 
over-engineering of ear-training problem. There are a lot of ear-training tools developed even
on mobile platforms which can make them more convenient to use because of their portability feature.  

The reasons for this tool are:
* I'm a Java developer, and I just wanted to get know more ~~Python~~ Kotlin (...)
Besides, I'm kind of interested in technical side of programming languages that I'm using at work, 
so designing and implementing a custom language from scratch would be a nice experience. 
* I'm a musician also and no one of available tools is suitable for me. 
I'm church organist, and most of my work bases on dialogue between me and priest. He can
sing melodies in different keys, and it requires me to answer keeping the same key. 
My tool allows me to create scenarios that can pick one key randomly and play melody 
waiting for my answer (basing on input from microphone).
* As a musician I'm also keen on physic nature of sounds and relations between them. 
The tool allows me as a user to build (_synthesize_) my custom sound starting with pure sine waves.
It is great to see how the nature of sound works.
* The tool is lightweight - one of the core assumptions is to use as few 3rd party dependencies, frameworks and libraries
as possible. Thanks to that, the base modules: `smnp.midi` and `smnp.synth` 
which correspond to MIDI engine and synthesising engine respectively are implemented using plain old Java library, nothing else.

## Getting started
Take a look at the following examples. Feel also free to dive into `examples/` directory to find more examples.

### Hello, world!
The must-be program of each programming language. 
The _SMNP_ implementation would look something like this:
```php
import smnp.io;

println("Hello, world!"); # Hello, world!
```

### Play some music
Following example shows the note literals as well as the staff syntax
used to produce a polyphonic melody of _Prząśniczka_ - a Polish song composed by Stanisław Moniuszko.
The staffs are being consumed by `midi` function from `smnp.audio.midi` module which
puts the notes on your system's MIDI engine and eventually plays the song.
```php
import smnp.io;
import smnp.audio.midi;

println("Prząśniczka");
println("by S. Moniuszko");

S = $ 4/4 @a:4d @a:8 @c5 @d5         | @e5:2 @a @a  | @f5:2 @e5 @d5       | @e5:2d @e5 |
          @e5:4d @h:8 @e5 @h         | @c5:2 @a @c5 | @e5:2 @h @h         | @c5:2d @e5 |
          @g5:4d @f5:8 @e5:4d @d5:8  | @c5:2d @c5   | @d5:4d @d5:8 @h @g  | @e5:2d 4   |
          @e5:4d @f5:8 @e5:4d @d#5:8 | @e5:2 @a5:2  | @f5:4d @d5:8 @h @e5 | @a:2d 4    ||;

A = $ 4/4 1                          | @a:2 2       | @a:2 2              | @a:2d 4    |
          @g#:4d @g#:8 @g# @g#       | @a:2 2       | @g#:2 @g# @g#       | @a:2d 4    |
          1                          | 1            | @h:4d @h:8 @g 4     | @c5:2d 4   |
          1                          | 1            | 1                   | 1          ||;

midi({ bpm -> 284 }, S, A);

```


## Documentation
Documentation is edited with _GitHub Wiki Pages_ and is available [here](https://github.com/bartlomiej-pluta/smnp/wiki).

## Disclaimers
1. Readability of the code and its structure is one of most important things
related to educational aspect of the project. In spite of having huge negative impact 
on efficiency of the tool, according to one of the assumptions 
it has much higher priority. So don't be surprised 
if the tool turns out to be extremely slow or ineffective.
