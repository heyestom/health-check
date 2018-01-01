# health-check

A [re-frame](https://github.com/Day8/re-frame) application for polling health-check endpoints and giving at a glance feedback about the state of the services in all the environments.

## To use:

This needs to be sorted so that there is an actual ready to go built artefact but for now...

run:
```
lein clean
lein cljsbuild once min
```

Open index.html in a browser, upload all the services you wish to monitor via the settings page, currently only supports CSV format, ranking them in order of severity (1 is most severe so - probably what you want for your prod services), and then switch to the monitoring tab.

Health Check will periodically ping all the urls it has been provided with and will display any which don't respond with a 2** in a big, red, hard to misconstrue box.

The skeleton of this project was generated using [Day8's re-frame-template](https://github.com/Day8/re-frame-template) which is awesome. :)

## Development Mode

### Start Cider from Emacs:

Put this in your Emacs config file:

```
(setq cider-cljs-lein-repl
	"(do (require 'figwheel-sidecar.repl-api)
         (figwheel-sidecar.repl-api/start-figwheel!)
         (figwheel-sidecar.repl-api/cljs-repl))")
```

Navigate to a clojurescript file and start a figwheel REPL with `cider-jack-in-clojurescript` or (`C-c M-J`)

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

### Run tests:

```
lein clean
lein doo phantom test once
```

The above command assumes that you have [phantomjs](https://www.npmjs.com/package/phantomjs) installed. However, please note that [doo](https://github.com/bensu/doo) can be configured to run cljs.test in many other JS environments (chrome, ie, safari, opera, slimer, node, rhino, or nashorn).

## Production Build


To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```
