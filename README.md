# pwcopy

Copies passwords from bitwarden to mac keychain and from there to
clipboard. Bitwarden session never expires. That is why this tool
stores it to a mac keychain that automatically locks after certain time of
intactivity.

# installation

Install bitwardein CLI and clojure.

# usage

First create a keychain called "secret" with keychain app. Set it to
lock after 5 minutes of inactivity.

Create bitwarden session by running `pwcopy store-new-bitwarden-session-key-to-keychain`.

Then run `pwcopy` to get list of available commands.

Consider binding hotkeys for certain passwords for example with https://github.com/koekeishiya/skhd.
