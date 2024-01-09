# pwcopy

Copies passwords from bitwarden to mac keychain and from there to
clipboard. Bitwarden session never expires. That is why this tool
stores it to a mac keychain that automatically locks after certain time of
intactivity.

# Installation

Install Bitwarden CLI and Clojure.

Create a keychain called "secret" with the keychain app. Set it to
lock after 5 minutes of inactivity.

Optionally add symlink to your `~/bin` with `ln -s <path to this repository>/pwcopy ~/bin/pwcopy`.

## Using with skhd

Consider binding hotkeys for certain passwords with https://github.com/koekeishiya/skhd.
If using skhd, you must make sure bitwarden cli is in path. And example binding:

```
rcmd - p : PATH="$HOME/.nvm/versions/node/v16.13.2/bin:$PATH"; pwcopy copy-password-from-keychain-to-clipboard <password id>
```

# Usage

Create bitwarden session by running `pwcopy store-new-bitwarden-session-key-to-keychain`.

Then run `pwcopy` to get list of available commands.

Password ids can be obtained with `bw list items --url <some url>`.
