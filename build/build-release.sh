#!/bin/sh

# hacky artefact bulidng script

# complie javascrpt
lein clean && lein cljsbuild once min

# shrink css
lein minify-assets

# inline the css
echo "Inline css...";
echo "<style media=\"screen\" type=\"text/css\">" >> headers.tmp;
cat ./resources/public/css/health-check.min.css >> headers.tmp;
echo  "</style>" >> headers.tmp;

# inline the minified javascript
echo "Inline js...";
echo '<script type="text/javascript">' >> headers.tmp;
cat ./resources/public/js/compiled/app-min.js >> headers.tmp;
echo '</script>' >> headers.tmp;

sed '/.*\<head.*/ r headers.tmp' ./build/health-check-release-template.html  > health-check-release.html ;

echo "tidying up";
rm headers.tmp;
