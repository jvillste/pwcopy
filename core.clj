(ns core
  (:require [babashka.process :as process]
            [clojure.string :as string]))

(require '[babashka.process.pprint]) ;; to make proesses printable

(def bitwarden-account "bitwarden")

(defn store-password-to-keychain [account password]
  (process/shell {:out :string :err :string} (str "security add-internet-password -U -s 'system' -a '" account "' -w '" password "' '" (System/getProperty "user.home") "/Library/Keychains/secret.keychain-db'")))

(defn get-password-from-key-chain [account]
  (string/trim (:out (process/shell {:out :string :err :string} (str "security find-internet-password -s 'system' -a '" account "' -w '" (System/getProperty "user.home") "/Library/Keychains/secret.keychain-db'")))))

(defn get-new-session-key []
  (string/trim (:out (process/shell {:out :string} "bw unlock --raw" ))))

(defn get-password-from-bitwarden [id]
  (:out (process/sh (str "bw get password " id " --session " (get-password-from-key-chain bitwarden-account)))))

(defn notify-completion! []
  (process/process "afplay /System/Library/Sounds/Ping.aiff")
  (process/process "osascript -e 'display notification \"copied\""))

(defn write-to-clipboard [text]
  (.setContents (.getSystemClipboard (java.awt.Toolkit/getDefaultToolkit))
                (java.awt.datatransfer.StringSelection. text)
                nil))


;;;;;; CLI

(defn store-new-bitwarden-session-key-to-keychain
  "Run this before other commands"
  []
  (store-password-to-keychain bitwarden-account (get-new-session-key)))

(defn copy-password-from-bitwarden-to-clipboard [password-id]
  (write-to-clipboard (get-password-from-bitwarden password-id))
  (notify-completion!))

(defn copy-password-from-bitwarden-to-keychain [password-id]
  (store-password-to-keychain password-id
                              (get-password-from-bitwarden password-id)))

(defn copy-password-from-keychain-to-clipboard [password-id]
  (write-to-clipboard (get-password-from-key-chain password-id))
  (notify-completion!))


(def commands [#'store-new-bitwarden-session-key-to-keychain
               #'copy-password-from-bitwarden-to-clipboard
               #'copy-password-from-bitwarden-to-keychain
               #'copy-password-from-keychain-to-clipboard])

(defn -main [& command-line-arguments]
  (let [[command-name & arguments] command-line-arguments]
    (if-let [command (first (filter (fn [command]
                                      (= command-name
                                         (name (:name (meta command)))))
                                    commands))]
      (try (apply command arguments)
           (finally Exception
                    (.flush *out*)
                    (shutdown-agents)))

      (do (println "Usage:")
          (println "------------------------")
          (println (->> commands
                        (map (fn [command-var]
                               (str (:name (meta command-var))
                                    ": "
                                    (:arglists (meta command-var))
                                    (when-let [doc (:doc (meta command-var))]
                                      (str "\n" doc)))))
                        (string/join "\n------------------------\n")))))))
