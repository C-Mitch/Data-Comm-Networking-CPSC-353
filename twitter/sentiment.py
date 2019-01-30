import twitter


def connect():
    CONSUMER_KEY = 'riNURrSr2j1lqnnHO2y6dC0Kq'
    CONSUMER_SECRET = 'A7fJWepPBWD48QgjThonKxp5fw1uFa8vnl5dvDBKjyWPkI2zxm'
    OAUTH_TOKEN = '1047521451371200512-yfQ7SwgoUToTk2PpVLSGQ1WVZxJsZB'
    OAUTH_TOKEN_SECRET = 'Qvz7CgyofcPzaRravnE1nPWsPSk71VakfVt70ilv4h90Z'
    auth = twitter.oauth.OAuth(OAUTH_TOKEN, OAUTH_TOKEN_SECRET,
                               CONSUMER_KEY, CONSUMER_SECRET)

    return twitter.Twitter(auth=auth)


def searchWords(twitter_api, q):
    count = 1000

    # See https://dev.twitter.com/docs/api/1.1/get/search/tweets

    search_results = twitter_api.search.tweets(q=q, count=count)

    statuses = search_results['statuses']

    # Iterate through 5 more batches of results by following the cursor

    for _ in range(5):
        try:
            next_results = search_results['search_metadata']['next_results']
        except KeyError:  # No more results when next_results doesn't exist
            break

        # Create a dictionary from next_results, which has the following form:
        # ?max_id=313519052523986943&q=NCAA&include_entities=1
        kwargs = dict([kv.split('=') for kv in next_results[1:].split("&")])

        search_results = twitter_api.search.tweets(**kwargs)
        statuses += search_results['statuses']

    status_texts = [status['text']
                    for status in statuses]

    # Compute a collection of all words from all tweets
    words = [w
             for t in status_texts
             for w in t.split()]

    return words


def sentScore(words):
    sent_file = open('AFINN-111.txt')

    scores = {}  # initialize an empty dictionary
    for line in sent_file:
        term, score = line.split("\t")
        # The file is tab-delimited.
        # "\t" means "tab character"
        scores[term] = int(score)  # Convert the score to an integer.

    score = 0
    for word in words:
        uword = word.encode('utf-8')
        if uword in scores.keys():
            score = score + scores[word]
    return float(score)


def main():
    api = connect()
    q = raw_input('Enter search term 1: ')
    words1 = searchWords(api, q)
    score1 = sentScore(words1)

    q2 = raw_input('Enter search term 2: ')
    words2 = searchWords(api, q2)
    score2 = sentScore(words2)

    if score1 > score2:
        print (q + " has a higher score at " + str(score1)
               + " than " + q2 + " at " + str(score2))
    else:
        print (q2 + " has a higher score at " + str(score2)
               + " than " + q + " at " + str(score1))


main()
