# pwcopy

Copies passwords from bitwarden to mac keychain and from there to
clipboard. Bitwarden session never expires. That is why this tool
stores it to a mac keychain that automatically locks after certain time of
intactivity.

# Installation

Install Bitwarden CLI and Clojure.

Create a keychain called "secret" with keychain app. Set it to lock
after 5 minutes of inactivity.

Optionally add symlink to your `~/bin` with `ln -s <path to this repository>/pwcopy ~/bin/pwcopy`.

Consider binding hotkeys for certain passwords for example with https://github.com/koekeishiya/skhd.

# Usage

Create bitwarden session by running `pwcopy store-new-bitwarden-session-key-to-keychain`.

Then run `pwcopy` to get list of available commands.
