let gameId = document.getElementById("gameId").getAttribute("value");

let reviewsSection = document.getElementById("reviews");

fetch(`http://localhost:8080/api/${gameId}/reviews`)
.then((response) => response.json())
.then((body) => {
    if (body.length > 0) {
        reviewsSection.innerHTML = '<h4 class="mt-4">Customer Reviews:</h4>';

    for (review of body) {
        let reviewHtml = '<div class="border-top mt-4 pt-3 border-info">\n';
        reviewHtml += '    <div class="mb-3">\n';
        reviewHtml += '        <p class="font-size-16px">\n';
        reviewHtml += '            Review by <span class="font-weight-bold">' + review.author + '</span>\n';
        reviewHtml += '            <br/>\n';
        reviewHtml += '            created on <span class="font-italic">' + review.created + '</span>\n';
        reviewHtml += '            <br/>\n';
        reviewHtml += '            <span class="text-warning">\n';

        for (let star = 1; star <= 5; star++) {
            const starSymbol = star <= review.stars ? '★' : '☆';
            reviewHtml += '                ' + starSymbol + '\n';
        }

        reviewHtml += '            </span>\n';
        reviewHtml += '        </p>\n';
        reviewHtml += '        <p class="mb-0">' + review.textContent + '</p>\n';
        reviewHtml += '    </div>\n';
        reviewHtml += '</div>';
        reviewsSection.innerHTML += reviewHtml;
    }
} else {
    reviewsSection.innerHTML = '<p class="mt-4 alert alert-info">No reviews available yet.</p>';
}
}) ;