RoundProgressView
=================

## About ##

An elegant way to indicate progress. For Android, but intended for Google Glass GDK.

## Usage ##
### Including In Your Project ###
Simply put `RoundProgressView.java` in with the rest of your source. The package currently defined in it is `com.kaiser.pendergrast.view`, but you can simply edit that to be whatever you want.

### Basic Usage ###
Put a RoundProgressView in your UI in whichever way you prefer, whether it be in XML or Java.

Call `setProgress(double progress)` to set the RoundProgressView's progress level, where progress is from 0 to 1.

If you would like progress to advance automatically, you can use `startAnimatingProgress(long duration, boolean loop)` where `duration` is how long one it should take to go from 0 to 1 progress level. `loop` allows you to create show indeterminant progress, and have the RoundProgressView animate indefinetely. You can stop the RoundProgressView by calling `stopAnimatingProgress()`.

### More Advanced Usage ###
`setReversed(boolean reversed)` allows the RoundProgressView to switch between animating from empty to full, to animating from full to empty.

`ProgressListener` is an interface that can be implemented that has callbacks for when the RoundProgressView is animating and 
* Finishes Animating: `onCompleted()`
* Loops Animating: `onLooped()`
* Updates Progress during an Animation: `onUpdate(double progress)`


