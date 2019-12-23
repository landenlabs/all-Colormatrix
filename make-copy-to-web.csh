

./gradlew assemble
cp app/build/outputs/apk/debug/app-debug.apk ~/private/websites/landenlabs-ipage/android/all-colormagtrix/colormagtrix.apk

./gradlew clean
rm all-colormagtrix-src.zip
source make-zip.csh
cp all-colormagtrix-src.zip ~/private/websites/landenlabs-ipage/android/all-colormagtrix/
 
