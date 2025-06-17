(ns http.header
  (:require clojure.string)
  (:require clojure.core.reducers))
  
;; [RFC 2616]
;; RESPONSE = Status-Line
;;            * (( general-header
;;              | response-header
;;              | entity-header ) CRLF)
;;              CRLF
;;              [ message-body ]

(def crlf "\r\n")

(def mimeType {
"aac" "audio/aac"
"abw" "application/x-abiword"
"apng" "image/apng"
"arc" "application/x-freearc"
"avif" "image/avif"
"avi" "video/x-msvideo"
"azw" "application/vnd.amazon.ebook"
"bin" "application/octet-stream"
"bmp" "image/bmp"
"bz" "application/x-bzip"
"bz2" "application/x-bzip2"
"cda" "application/x-cdf"
"csh" "application/x-csh"
"css" "text/css"
"csv" "text/csv"
"doc" "application/msword"
"docx" "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
"eot" "application/vnd.ms-fontobject"
"epub" "application/epub+zip"
; Note, Windows and macOS upload .gz files with the non-standard MIME type application/x-gzip.
"gz" "application/gzip"
"gif" "image/gif"
"htm" "text/html"
"html" "text/html"
"ico" "image/vnd.microsoft.icon"
"ics" "text/calendar"
"jar" "application/java-archive"
"jpeg" "image/jpeg"
"jpg" "image/jpeg"
;  Specifications: HTML and RFC 9239
"js" "text/javascript"
"json" "application/json"
"jsonld" "application/ld+json"
"md" "text/markdown"
"mid" "audio/midi"
"midi" "audio/midi"
"mjs" "text/javascript"
"mp3" "audio/mpeg"
"mp4" "video/mp4"
"mpeg" "video/mpeg"
"mpkg" "application/vnd.apple.installer+xml"
"odp" "application/vnd.oasis.opendocument.presentation"
"ods" "application/vnd.oasis.opendocument.spreadsheet"
"odt" "application/vnd.oasis.opendocument.text"
"oga" "audio/ogg"
"ogv" "video/ogg"
"ogx" "application/ogg"
"opus" "audio/ogg"
"otf" "font/otf"
"png" "image/png"
"pdf" "application/pdf"
"php" "application/x-httpd-php"
"ppt" "application/vnd.ms-powerpoint"
"pptx" "application/vnd.openxmlformats-officedocument.presentationml.presentation"
"rar" "application/vnd.rar"
"rtf" "application/rtf"
"sh" "application/x-sh"
"svg" "image/svg+xml"
"tar" "application/x-tar"
"tif" "image/tiff"
"tiff" "image/tiff"
"ts" "video/mp2t"
"ttf" "font/ttf"
"txt" "text/plain"
"vsd" "application/vnd.visio"
"wav" "audio/wav"
"weba" "audio/webm"
"webm" "video/webm"
"webmanifest" "application/manifest+json"
"webp" "image/webp"
"woff" "font/woff"
"woff2" "font/woff2"
"xhtml" "application/xhtml+xml"
"xls" "application/vnd.ms-excel"
"xlsx" "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
; application/xml is recommended as of RFC 7303 (section 4.1), but text/xml is still used sometimes. You can assign a specific MIME type to a file with .xml extension depending on how its contents are meant to be interpreted. For instance, an Atom feed is application/atom+xml, but application/xml serves as a valid default.
"xml" "application/xml"
"xul" "application/vnd.mozilla.xul+xml"
; . Note, Windows uploads .zip files with the non-standard MIME type application/x-zip-compressed.
"zip" "application/zip"
; audio/3gpp if it doesn't contain video
"3gp" "video/3gpp"
; audio/3gpp2 if it doesn't contain video
"3g2" "video/3gpp2"
"7z" "application/x-7z-compressed"
"" nil
nil nil
})

(def headerFields {
  })

(defn parseHeader
  ([] {})
  ([header] (let [headerList (clojure.string/split header #"\r\n\r\n" -1)]
              (clojure.core.reducers/fold (fn ([] {})
                        ([head headerMap] (let [parts (clojure.string/split head #": " 2)]
                          (assoc headerMap (first parts) (last parts)))))
                    headerList
                 ))))

(defn header
  ([] crlf)
  ([k] (if (coll? k)
         (if (empty? k)
           crlf
           (if (> 2 (count k))
            crlf
            (str (apply header k) crlf)))
         crlf))
  ([k v] (str k ":" v crlf))
  ([k v & more] (str (header k v) (apply header more))))


(defn mime-from-filename
  ([] crlf)
  ([filename] (let [fileEnding (last (clojure.string/split filename #"\." -1))]
    (mimeType fileEnding))))
