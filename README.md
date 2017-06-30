# datomic-riemann

A Riemann metrics client wrapper for use with Datomic. Uses unencrypted TCP, not for use on public networks!

## Usage

1. Build a jar: `lein uberjar`
2. Place the standalone jar on the Datomic classpath (`lib/`)
3. In your Datomic properties file, [set the metrics callback](http://docs.datomic.com/monitoring.html#sec-2) ie: `metrics-callback=my.ns/handler`
4. Configure logging in Datomic's `bin/logback.xml`, for example: `<logger name="datomic-riemann" level="INFO"/>`
5. Run Datomic with the following env vars set:
   * `RIEMANN_HOST=<address/dns name of your Riemann server>` (required)
   * `RIEMANN_PORT=<Riemann server port>` (optional)
   * `RIEMANN_EVENT_HOST=<custom :host value for Riemann events>` (optional)

The callback will drop events (with a warning in the log) if the metrics client is not connected. This differs from the default behavior of `riemann-clojure-client`, and may change in the future.

## License

Copyright © 2017 Yet Analytics Inc.

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
